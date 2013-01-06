package org.ad13.denarius.controller;

import java.math.BigDecimal;
import java.util.List;

import org.ad13.denarius.dto.StatisticsDTO;
import org.ad13.denarius.model.Account;
import org.ad13.denarius.model.AccountEntry;
import org.ad13.denarius.repository.AccountRepository;
import org.ad13.denarius.service.DenariusUserDetailsService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for statistcal information.
 * 
 * @author Andrew Dodd
 */
@Controller
@Transactional
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    private AccountRepository accountsRepository;

    @Autowired
    private DenariusUserDetailsService userDetailsService;

    // @Autowired
    // private TransactionRepository transactionsRepository;

//    @RequestMapping(value = "/get/{year}/{month}", method = RequestMethod.GET)
//    @ResponseBody
//    public StatisticsDTO getStatisticsForMonth(@PathVariable int year, @PathVariable short month) {
//        List<Account> accounts = accountsRepository.findAllByOwner(userDetailsService.currentUser());
//
//        BigDecimal accountTotal = BigDecimal.ZERO, accountTotalMonthStart = BigDecimal.ZERO, accountTotalMonthEnd = BigDecimal.ZERO;
//
//        for (Account acc : accounts) {
//            // Get the total account value
//            AccountEntry entry = accountsRepository.getAccountValue(acc.getAccountId());
//            if (entry != null) {
//                accountTotal = accountTotal.add(entry.getValue());
//            }
//
//            // Get the total at the month start
//            entry = accountsRepository.getAccountValue(acc.getAccountId(), new LocalDate(year, month, 1));
//            if (entry != null) {
//                accountTotalMonthStart = accountTotalMonthStart.add(entry.getValue());
//
//                // And end (it can't be null here)
//                entry = accountsRepository.getAccountValue(acc.getAccountId(), new LocalDate(year, month, 1)
//                        .plusMonths(1).minusDays(1));
//                accountTotalMonthEnd = accountTotalMonthEnd.add(entry.getValue());
//            }
//        }
//
//        BigDecimal accountVariation = accountTotalMonthEnd.subtract(accountTotalMonthStart);
//
//        StatisticsDTO stats = new StatisticsDTO(accountTotal, new BigDecimal("1234.56"), new BigDecimal("1500.00"),
//                new BigDecimal("233"), accountVariation);
//
//        return stats;
//    }
}
