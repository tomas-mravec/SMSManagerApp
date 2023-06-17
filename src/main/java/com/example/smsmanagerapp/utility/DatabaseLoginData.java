package com.example.smsmanagerapp.utility;

public  class DatabaseLoginData {

  private static String url = "jdbc:mysql://localhost:3306/sms_manager";
  private static String username = "root";
  private static String password = "heslo123";

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getUrl() {
        return url;
    }
}
