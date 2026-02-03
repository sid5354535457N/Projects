package views;

import dao.UserDAO;
import service.GenerateOtp;
import service.SendOTPService;
import service.UserService;
import structure.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class Welcome {
    public void welcomeScreen() {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to our Application");
        System.out.println("press 1 to login              press 2 to signup");
        System.out.println("press 3 to exit");
        int choice=0;
        try{
            choice=Integer.parseInt(br.readLine());
        }catch (IOException ex) {
            ex.printStackTrace();
        }
        switch (choice) {
            case 1:login();
                    break;

            case 2:signup();
                    break;

            case 3:System.exit(0);
        }
    }

    private void signup() {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter your name:");
        String name=sc.nextLine();
        System.out.println("Enter email");
        String email=sc.nextLine();
        String genotp=GenerateOtp.getOtp();
        SendOTPService.sendOTP(email,genotp);
        System.out.println("Enter otp:");
        String otp=sc.nextLine();
        if(otp.equals(genotp)) {
            User user=new User(name,email);
            int response= UserService.saveUser(user);
            switch (response) {
                case 0:
                    System.out.println("User registered");
                    //break;

                case 1:
                    System.out.println("User already exist");
                    //break;
            }
        }else {
            System.out.println("Wrong otp");
        }
    }

    private void login() {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter your email:");
        String email=sc.nextLine();
        try {
            if(UserDAO.isExist(email)) {
                String genotp= GenerateOtp.getOtp();
                SendOTPService.sendOTP(email,genotp);
                System.out.println("Enter OTP:");
                String otp=sc.nextLine();
                if(otp.equals(genotp)) {
                    new UserView(email).home();
                }else {
                    System.out.println("Incorrect OTP");
                }
            }else {
                System.out.println("User not exist");
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
