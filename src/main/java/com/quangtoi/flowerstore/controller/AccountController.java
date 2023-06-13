package com.quangtoi.flowerstore.controller;

import com.quangtoi.flowerstore.dto.AccountDto;
import com.quangtoi.flowerstore.dto.OrderDto;
import com.quangtoi.flowerstore.dto.RoleDto;
import com.quangtoi.flowerstore.exception.ResourceNotFoundException;
import com.quangtoi.flowerstore.model.Account;
import com.quangtoi.flowerstore.service.AccountService;
import com.quangtoi.flowerstore.service.OrderService;
import com.quangtoi.flowerstore.utils.Utility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/accounts")
@CrossOrigin
public class AccountController {
    private AccountService accountService;
    private OrderService orderService;
    private ModelMapper mapper;
    private JavaMailSender mailSender;

    //test ok
    @Operation(
            summary = "Get all accounts REST API"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        return ResponseEntity.ok().body(accountService.getAllAccounts());
    }

    //test ok
    @Operation(
            summary = "Get account by id REST API"
    )
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable("id")Long id){
        return ResponseEntity.ok().body(accountService.getAccount(id));
    }

    //test ok
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(
            summary = "Update account REST API"
    )
    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> updateAccount(@RequestBody AccountDto accountDto, @PathVariable("id")Long id){
        return ResponseEntity.ok().body(accountService.updateAccount(accountDto, id));
    }

    //test ok
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(
            summary = "Add role to account REST API"
    )
    @PutMapping("/add-role/{id}")
    public ResponseEntity<AccountDto> addRoleToAccount(@PathVariable("id")Long id, @RequestBody RoleDto roleDto){
        AccountDto accountDto = accountService.getAccount(id);
        Account account = mapper.map(accountDto, Account.class);
        return ResponseEntity.ok().body(accountService.addRoleToAccount(account, roleDto));
    }

    //test ok
    @Operation(summary = "Change password REST API")
    @PutMapping("/change-password/{id}")
    public ResponseEntity<AccountDto> changePassword(@PathVariable("id")Long id, @RequestParam String password){
        AccountDto accountDto = accountService.getAccount(id);
        AccountDto accountDtoUpdated = accountService.changePassword(mapper.map(accountDto, Account.class), password);
        return ResponseEntity.ok().body(accountDtoUpdated);
    }


    //test ok
    @Operation(summary = "Get all orders by account REST API")
    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderDto>> getAllOrdersByAccount(@PathVariable("id") Long id){
        Account account = mapper.map(accountService.getAccount(id), Account.class);
        return ResponseEntity.ok().body(orderService.getAllOrdersByAccount(account));
    }

    //test ok
    @Operation(summary = "process forgot password REST API")
    @PostMapping("/forgot-password")
    public ResponseEntity<String> processForgotPassword(HttpServletRequest request, @RequestParam String email){
        String token = RandomString.make(45);
        try {
            accountService.updateResetPassword(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/api/v1/accounts/reset-password?token=" + token;

            sendMail(email, resetPasswordLink);
            return ResponseEntity.ok().body(resetPasswordLink);
        }catch (ResourceNotFoundException | MessagingException e){
            return ResponseEntity.badRequest().body("Resource not found with email " + email);
        }
    }

    //test ok
    @Operation(summary = "Check reset password token is valid REST API")
    @GetMapping("/reset-password")
    public ResponseEntity<String> checkTokenIsValid(@RequestParam String token){
        Account account = accountService.getByResetPasswordToken(token);
        if (account != null){
            return ResponseEntity.ok().body("Token is valid!");
        }
        return ResponseEntity.badRequest().body("Token is invalid!");
    }

    //test ok
    @Operation(summary = "Reset password REST API")
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(HttpServletRequest request){
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        Account account = accountService.getByResetPasswordToken(token);
        if (account != null){
            accountService.changePassword(account, password);
            return ResponseEntity.ok().body("Reset password successfully!");
        }
        return ResponseEntity.badRequest().body("Token is invalid");
    }

    private void sendMail(String email, String resetPasswordLink) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("2051052140toi@ou.edu.vn");
        helper.setTo(email);

        String subject = "Here is the link to reset your password";
        String content = "<p>Hello,</p>"+
                "<p>You have request to reset your password.</p>"
                + "<p>Click link below to change your password:</p>"
                + "<p><b><a href=\"" + resetPasswordLink + "\">Change my password</a></b></p>"
                + "<p>Ignore this email if you do remember your password, or you have not make a request</p>";

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }



}
