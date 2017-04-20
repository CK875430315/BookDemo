package com.dianzhi.yxt.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Core {
	public TelephonyManager tmManager;
	public WifiManager wifi;
	
	public Core(Context context) {
		tmManager = (TelephonyManager) context
				.getSystemService("phone");
		wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
	}
	
	/**
	 * 获取IMEI
	 */
	public String getIMEI() {
		return tmManager.getDeviceId()+"";
	}
	/****
	 * cup信息
	 */
	public String getCPUinfo() {
		String str1 = "/proc/cpuinfo";
		String str2 = "";
		String[] cpuInfo = { "", "" }; // 1-CPU型号 //2-CPU频率
		String[] arrayOfString;
		try {
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			for (int i = 2; i < arrayOfString.length; i++) {
				cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
			}
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			cpuInfo[1] += arrayOfString[2];
			localBufferedReader.close();
		} catch (IOException e) {
		}
		return (cpuInfo[0].trim().replaceAll(" ", "")).toString();
	}
	
	public String getIMSI(){
		return tmManager.getSubscriberId()+""; 
	}
	
	public String getMac() {
		return wifi.getConnectionInfo().getMacAddress()+""; 
	}
	
	public String getIP() {
		WifiInfo wifiInfo = wifi.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		int[] ipAddr = new int[4];
		ipAddr[0] = ipAddress & 0xFF;
		ipAddr[1] = (ipAddress >> 8) & 0xFF;
		ipAddr[2] = (ipAddress >> 16) & 0xFF;
		ipAddr[3] = (ipAddress >> 24) & 0xFF;
		return new StringBuilder().append(ipAddr[0]).append(".")
				.append(ipAddr[1]).append(".").append(ipAddr[2]).append(".")
				.append(ipAddr[3]).toString();
	}
	
	public String getMob() {
		return android.os.Build.MODEL;
	}
	
	public static String getPhpStamp() {
		String stamp = String.valueOf(System.currentTimeMillis());
		stamp = stamp.substring(0, stamp.length()- 3);
		return stamp;
	}
	
	/**
	 * 得到tgid里的推广号
	 */
	public static String getAssetUid(Context context) {
		String result = "";
		try {
			String[] list = context.getResources().getAssets().list("");
			for (String fname : list) {
				if(fname.equals("tgid.txt")) {
					InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open("tgid.txt"));
					BufferedReader bufReader = new BufferedReader(inputReader);
					String line = "";
					while ((line = bufReader.readLine()) != null) {
						result += line;
					}
					break;
				}
			}
			Log.w("getAssetUid ========== ", result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//ControlUtil.showToastDialog(context, "asset�ļ��������tgid.txt, �ļ�������:"+result, null);
		return result;
	}
	
	/*
	 * MD5编码
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString().toLowerCase();
	}
	
    // 判断应用是否安装
    /*public static boolean isAvilible(Context context, String packageName)
    {
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for ( int i = 0; i < pinfo.size(); i++ )
        {
            if(pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }*/
    
    public static void setUmengChannel(Context context, String channel) {
    	ApplicationInfo appInfo;
	    try {
			appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
					PackageManager.GET_META_DATA);
			appInfo.metaData.putString("UMENG_CHANNEL", channel);
		} catch (NameNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
    public static String getUmengChannel(Context context) {
    	ApplicationInfo appInfo;
    	String str = "";
	    try {
			appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
					PackageManager.GET_META_DATA);
			str = appInfo.metaData.getString("UMENG_CHANNEL");
//			if(TextUtils.isEmpty(str)) {
//				str = String.valueOf(appInfo.metaData.getInt("UMENG_CHANNEL"));
//			}
		} catch (NameNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    return str;
    }
	
}

