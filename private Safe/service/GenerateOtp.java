package service;

import java.util.Random;

public class GenerateOtp {
    public static String getOtp() {
        Random random=new Random();
        return String.format("%04d",random.nextInt(10000));
    }

}
