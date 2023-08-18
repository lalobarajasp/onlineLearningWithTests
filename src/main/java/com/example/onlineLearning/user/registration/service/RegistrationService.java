package com.example.onlineLearning.user.registration.service;

import com.example.onlineLearning.course.model.Course;
import com.example.onlineLearning.user.appUser.model.AppUser;
import com.example.onlineLearning.user.appUser.model.AppUserRole;
import com.example.onlineLearning.user.appUser.repository.AppUserRepository;
import com.example.onlineLearning.user.appUser.service.AppUserService;
import com.example.onlineLearning.user.email.EmailSender;
import com.example.onlineLearning.user.registration.model.RegistrationRequest;
import com.example.onlineLearning.user.registration.token.ConfirmationToken;
import com.example.onlineLearning.user.registration.token.ConfirmationTokenService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final AppUserService appUserService;
    private EmailValidator emailValidator;
    private ConfirmationTokenService confirmationTokenService;
    private AppUserRepository appUserRepository;
    private  final EmailSender emailSender;

    private  final BCryptPasswordEncoder bCryptPasswordEncoder;



    public String register(RegistrationRequest registrationRequest) {
        boolean isValidEmail = emailValidator.test(registrationRequest.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("email not valid");
        }
        String token = appUserService.singUpUser(new AppUser(
                registrationRequest.getName(),
                registrationRequest.getLastName(),
                registrationRequest.getEmail(),
                registrationRequest.getPassword(),
                registrationRequest.getPasswordCode(),
                AppUserRole.USER
        ));
        String link = "http://localhost:8080/registration/confirm?token=" + token;
        emailSender.send(
                registrationRequest.getEmail(),
                buildEmail(registrationRequest.getName(), link));

        return token;


    }


    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }

    //Retrieve All Registers
    public List<AppUser> getAllRegisters() {
        return appUserRepository.findAll();

    }

    //Retrieve Only a Register
    public AppUser getOnlyRegister(Long id) {
        return appUserRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("The register with id: " + id + " doesn't exist.")
        );
    }

    //Edit Profile
    public AppUser updateAppUser(Long id, String name, String lastName, String email, Long passwordCode){
        AppUser register = null;
        if(appUserRepository.existsById(id)){
            register = appUserRepository.findById(id).get();
            if (name != null) register.setName(name);
            if (lastName != null) register.setLastName(lastName);
            if (email != null) register.setEmail(email);
            if (passwordCode != null) register.setPasswordCode(passwordCode);
            appUserRepository.save(register);

        }else {
            System.out.println("Update | The register with the id: " + id + " doesn't exist.");
        }
        return register;
    }

    //Delete Register
    public AppUser deleteAppUser(Long id) {
        AppUser register = null;
        if (appUserRepository.existsById(id)) {
            register = appUserRepository.findById(id).orElseThrow();
            if (register.getDeletedAccount() == false) register.setDeletedAccount(true);
            appUserRepository.save(register);
        }else {
            System.out.println("Delete | The register with the id: " + id + " doesn't exist.");
        }
        return register;
    }

    //ChangePassword when you remember it
    public AppUser updatePassword(Long id, String password, String newPassword) {
        AppUser register = null;
        if (appUserRepository.existsById(id)) {
            register = appUserRepository.findById(id).get();
            if ((password != null) && (newPassword != null)) {
                boolean compare = bCryptPasswordEncoder.matches(password,register.getPassword());
                if (compare) {
                    String encodedPassword = bCryptPasswordEncoder.encode(register.getPassword());
                    newPassword = encodedPassword;
                    register.setPassword(newPassword);
                    appUserRepository.save(register);
                }else {
                    throw new IllegalStateException("Please review your password. Try again.");
                }
            }
            appUserRepository.save(register);

        } else {
            System.out.println("Update | The user with the id: " + id + " doesn't exist.");
        }
        return register;
    }

    public AppUser forgotPassword(Long id, Long passwordCode, String newPassword){
        AppUser register = null;
        if(appUserRepository.existsById(id)){
            register = appUserRepository.findById(id).get();
            if (passwordCode != null) {
                if (passwordCode.equals(register.getPasswordCode())) {
                    String encodedPassword = bCryptPasswordEncoder.encode(register.getPassword());
                    newPassword = encodedPassword;
                    register.setPassword(newPassword);
                    appUserRepository.save(register);
                }
            }
            appUserRepository.save(register);

        }else {
            System.out.println("Update | The register with the id: " + id + " doesn't exist.");
        }
        return register;
    }


    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }








}
