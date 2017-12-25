package com.andlp.lib;

import java.util.Map;

public class myClass {

    private static String publicKey;
    private static String privateKey;

    public static void main(String args[]) {
        System.out.println("Hello World!");
        try{setUp();}catch (Throwable t){t.printStackTrace();}
        try{add();}catch (Throwable t){t.printStackTrace();}
        try{jie();}catch (Throwable t){t.printStackTrace();}

    }




    public static void  setUp() throws Exception{//���ù�˽��Կ
        Map<String, Object> keyMap = RSACoder.initKey();
        publicKey = RSACoder.getPublicKey(keyMap);
        privateKey = RSACoder.getPrivateKey(keyMap);
        System.err.println("public: \n\r" + publicKey);
        System.err.println("private: \n\r" + privateKey);
    }


    public static void add()throws Exception{
        System.err.println("public add------private jie");
        String inputStr = "abc";
        byte[] data = inputStr.getBytes();

        byte[] encodedData = RSACoder.encryptByPublicKey(data, publicKey);

//        String jiaStr = new String(encodedData);
//        System.err.println(jiaStr.length()+",����:\r"+jiaStr );
        StringBuffer sb=new StringBuffer();
        for(int i=0; i<encodedData.length; i++){
            sb.append(encodedData[i]+"|");
        }
        System.out.println("���:"+sb.toString());

        byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData, privateKey);

        String outputStr = new String(decodedData);
        System.err.println("before add()" + inputStr + "\n\r" + "after jie() " + outputStr);
    }

    public static void jie()throws Exception{
        System.err.println("private add()------public jie()");//˽Կ����  ��Կ����
        String inputStr = "sign";
        byte[] data = inputStr.getBytes();

        byte[] encodedData = RSACoder.encryptByPrivateKey(data, privateKey);

//        for (byte  b:encodedData){
//            System.err.println("����:"+b );
//        }

        String jiaStr = new String(encodedData);
        System.err.println("str:" + jiaStr + "\n\r" + "  " );//
        System.err.println(jiaStr.length()+",����:\r" );


        byte[] decodedData = RSACoder .decryptByPublicKey(encodedData, publicKey);

        String outputStr = new String(decodedData);
        System.err.println("before  jie()" + inputStr + "\n\r" + "after jie() " + outputStr);//����֮ǰ  ����֮��


        System.err.println("private sign  ------  public voli sign");//˽Կǩ��  ��Կ��֤ǩ��
        // ����ǩ��
        String sign = RSACoder.sign(encodedData, privateKey);
        System.err.println("sign:\r" + sign);                                  //ǩ��

        // ��֤ǩ��
        boolean status = RSACoder.verify(encodedData, publicKey, sign);
        System.err.println("state:\r" + status);                              //״̬

    }


}
