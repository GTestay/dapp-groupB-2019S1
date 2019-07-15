package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.AccountManagerService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Loan;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.MoneylenderService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.UserService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.dtos.DepositByCreditCardInformation;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.dtos.TransactionInformation;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.dtos.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Transactional
public class UserController {

    private final UserService userService;

    @Autowired
    private AccountManagerService accountManagerService;

    @Autowired
    private MoneylenderService moneyLender;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User newUser) {
        return userService.createUser(newUser);
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User findUser(@PathVariable("id") Long id) {
        return userService.searchUser(id);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public User loginUser(@RequestBody UserLogin userLogin) {
        return userService.findUserByEmail(userLogin.getEmail());
    }

    @GetMapping("/users/emails")
    @ResponseStatus(HttpStatus.OK)
    public List<String> allEmailsContaining(@RequestParam(required = false, defaultValue = "", name = "email") String email) {
        return userService.allEmailsContaining(email);
    }

    @GetMapping("/users/{id}/balance")
    @ResponseStatus(HttpStatus.OK)
    public double userBalance(@PathVariable("id") Long id) {
        User user = userService.searchUser(id);

        return user.balance(accountManagerService);
    }

    @PostMapping("/users/{id}/takeOutLoan")
    @ResponseStatus(HttpStatus.CREATED)
    public Loan takeOutLoan(@PathVariable Long id) {
        User owner = userService.searchUser(id);

        return owner.takeOutALoan(moneyLender, accountManagerService);
    }

    @PostMapping("/users/{id}/madeDepositByCash")
    @ResponseStatus(HttpStatus.CREATED)
    public void madeDepositByCash(@PathVariable Long id, @RequestBody TransactionInformation transactionInformation) {
        User user = userService.searchUser(id);

        user.cashDeposit(transactionInformation.amount(), accountManagerService);
    }

    @PostMapping("/users/{id}/madeDepositByCreditCard")
    @ResponseStatus(HttpStatus.CREATED)
    public void madeDepositByCreditCard(@PathVariable Long id, @RequestBody DepositByCreditCardInformation depositByCreditInformation) {
        User user = userService.searchUser(id);

        user.creditDeposit(depositByCreditInformation.amount(),
                            depositByCreditInformation.dueDate(),
                            depositByCreditInformation.cardNumber(),
                            accountManagerService);
    }

    @PostMapping("users/{id}/requireCredit")
    @ResponseStatus(HttpStatus.CREATED)
    public void requireCredit(@PathVariable Long id, @RequestBody TransactionInformation transactionInformation) {
        User user = userService.searchUser(id);

        user.requireCredit(transactionInformation.amount(), accountManagerService);
    }
}
