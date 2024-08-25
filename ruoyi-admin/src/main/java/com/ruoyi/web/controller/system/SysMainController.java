package com.ruoyi.web.controller.system;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.ShiroConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.CookieUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.shiro.service.SysPasswordService;
import com.ruoyi.system.domain.XFinancial;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysMenuService;
import com.ruoyi.system.service.IXFinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 首页 业务处理
 * 
 * @author ruoyi
 */
@Controller
public class SysMainController extends BaseController
{
    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private SysPasswordService passwordService;


    @Autowired
    private IXFinancialService xFinancialService;
    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap)
    {


        List<XFinancial> records = xFinancialService.selectXFinancialList(new XFinancial());
        mmap.addAttribute("records", records);


        // 获取当前月份的YearMonth实例
        YearMonth currentMonth = YearMonth.now();
        MonthDay currentDay = MonthDay.now();
        //月收入支出
        BigDecimal payMonthMoney = records.stream()
                .filter(record -> {
                    // 将java.util.Date转换为LocalDate
                    LocalDate localDate = record.getRecordDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    // 检查日期是否在当前月份
                    return localDate.getMonth() == currentMonth.getMonth() &&
                            localDate.getYear() == currentMonth.getYear()
                            &&  record.getType().equals("支出");
                })
                .map(XFinancial::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalMonthMoney = records.stream()
                .filter(record -> {
                    LocalDate localDate = record.getRecordDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return localDate.getMonth() == currentMonth.getMonth() &&
                            localDate.getYear() == currentMonth.getYear()
                            &&  record.getType().equals("收入");
                })
                .map(XFinancial::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        //年收入
        BigDecimal totalYearMoney = records.stream()
                .filter(record -> {
                    LocalDate localDate = record.getRecordDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return localDate.getYear() == currentMonth.getYear()
                            &&  record.getType().equals("收入");
                })
                .map(XFinancial::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //总资产
        BigDecimal totalInMoney = records.stream()
                .filter(record -> record.getType().equals("收入"))
                .map(XFinancial::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalOutMoney = records.stream()
                .filter(record -> record.getType().equals("支出"))
                .map(XFinancial::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalMoney = totalInMoney.subtract(totalOutMoney.abs());
        //顶部统计
        mmap.put("payMonthMoney",payMonthMoney);
        mmap.put("totalMonthMoney",totalMonthMoney);
        mmap.put("totalYearMoney",totalYearMoney);
        mmap.put("totalMoney",totalMoney);


        //收入支出曲线图
        List<XFinancial> income = records.stream().filter(o -> o.getType().equals("收入")).collect(Collectors.toList());
        List<XFinancial> pay = records.stream().filter(o -> o.getType().equals("支出")).collect(Collectors.toList());
        

        List<List<Object>> incomes= new ArrayList<>();
        for (XFinancial xFinancial : income) {
            String format = new SimpleDateFormat("yyyy,MM,dd").format(xFinancial.getRecordDate());
            String[] split = format.split(",");
            incomes.add(Arrays.asList(gd(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])), xFinancial.getMoney()));
        }

        List<List<Object>> pays = new ArrayList<>();
        for (XFinancial xFinancial : pay) {
            String format = new SimpleDateFormat("yyyy,MM,dd").format(xFinancial.getRecordDate());
            String[] split = format.split(",");
            pays.add(Arrays.asList(gd(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])), xFinancial.getMoney()));
        }

        mmap.addAttribute("income", incomes);
        mmap.addAttribute("pay", pays);


        //日收入
        BigDecimal incomeTotal = records.stream()
                .filter(record -> {
                    LocalDate localDate = record.getRecordDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return localDate.getDayOfMonth() == currentDay.getDayOfMonth()
                            &&  record.getType().equals("收入");
                })
                .map(XFinancial::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expenditureTotal = records.stream()
                .filter(record -> {
                    LocalDate localDate = record.getRecordDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return localDate.getDayOfMonth() == currentDay.getDayOfMonth()
                            &&  record.getType().equals("支出");
                })
                .map(XFinancial::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        mmap.put("incomeTotal",incomeTotal);
        mmap.put("expenditureTotal",expenditureTotal);

        return "main";
    }

    @GetMapping("/system/getTotal")
    public ResponseEntity<Map<String, Object>>  getTotal(@RequestParam(name = "type") String type) {


        List<XFinancial> records = xFinancialService.selectXFinancialList(new XFinancial());
        // 获取当前月份的YearMonth实例
        YearMonth currentMonth = YearMonth.now();
        MonthDay currentDay = MonthDay.now();

        //月收入
        BigDecimal payMonthMoney = records.stream()
                .filter(record -> {
                    // 将java.util.Date转换为LocalDate
                    LocalDate localDate = record.getRecordDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    // 检查日期是否在当前月份
                    return localDate.getMonth() == currentMonth.getMonth() &&
                            localDate.getYear() == currentMonth.getYear()
                            &&  record.getType().equals("支出");
                })
                .map(XFinancial::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalMonthMoney = records.stream()
                .filter(record -> {
                    LocalDate localDate = record.getRecordDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return localDate.getMonth() == currentMonth.getMonth() &&
                            localDate.getYear() == currentMonth.getYear()
                            &&  record.getType().equals("收入");
                })
                .map(XFinancial::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        //年收入
        BigDecimal totalYearInMoney = records.stream()
                .filter(record -> {
                    LocalDate localDate = record.getRecordDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return localDate.getYear() == currentMonth.getYear()
                            &&  record.getType().equals("收入");
                })
                .map(XFinancial::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        BigDecimal totalYearOutMoney = records.stream()
                .filter(record -> {
                    LocalDate localDate = record.getRecordDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return localDate.getYear() == currentMonth.getYear()
                            &&  record.getType().equals("支出");
                })
                .map(XFinancial::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        //日收入
        BigDecimal incomeTotal = records.stream()
                .filter(record -> {
                    LocalDate localDate = record.getRecordDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return localDate.getDayOfMonth() == currentDay.getDayOfMonth()
                            &&  record.getType().equals("收入");
                })
                .map(XFinancial::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expenditureTotal = records.stream()
                .filter(record -> {
                    LocalDate localDate = record.getRecordDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return localDate.getDayOfMonth() == currentDay.getDayOfMonth()
                            &&  record.getType().equals("支出");
                })
                .map(XFinancial::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

//        TotalMoneyVo data = new TotalMoneyVo();
        Map<String, Object> data = new HashMap<>();
        if (type.equals("day")) {
            data.put("income", incomeTotal);
            data.put("expenditure", expenditureTotal);
        } else if (type.equals("month")) {
            data.put("income", payMonthMoney);
            data.put("expenditure", totalMonthMoney);
        } else if (type.equals("year")) {
            data.put("income", totalYearInMoney);
            data.put("expenditure", totalYearOutMoney);
        }

        return ResponseEntity.ok(data);
    }


    // 辅助函数，与JavaScript中的gd函数相同
    public static long gd(int year, int month, int day) {
        return new Date(year - 1900, month - 1, day).getTime();
        // 注意：Java的Date月份也是从0开始的，但年份是从1900开始的
    }


}
