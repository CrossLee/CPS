/*
 * Created on Feb 27, 2012
 *
 * DesUtils.java
 *
 * Copyright (C) 2012 by Cemso Software & Technology Services (Shanghai) Limited.
 * All rights reserved. Cemso Software & Technology Services (Shanghai) Limited 
 * claims copyright in this computer program as an unpublished work. Claim of copyright 
 * does not imply waiver of other rights.
 *
 * NOTICE OF PROPRIETARY RIGHTS
 *
 * This program is a confidential trade secret and the property of 
 * Cemso Software & Technology Services (Shanghai) Limited. Use, examination, 
 * reproduction, disassembly, decompiling, transfer and/or disclosure to others of 
 * all or any part of this software program are strictly prohibited except by express 
 * written agreement with Cemso Software & Technology Services (Shanghai) Limited.
 */
/*
 * ---------------------------------------------------------------------------------
 * Modification History
 * Date               Author                     Version     Description
 * Feb 27, 2012       gl65293                    1.0         New
 * ---------------------------------------------------------------------------------
 */
/**
 * 
 */
package com.cemso.util;

/**
 * @author gl65293
 *
 */
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * DES���ܺͽ��ܹ���,���Զ��ַ������м��ܺͽ��ܲ��� ��
 * 
 * @author cross
 *         <p>
 *         2012-2-27
 *         </p>
 */
public class DesUtils {

    /** �ַ���Ĭ�ϼ�ֵ */
    private static String strDefaultKey = "national";

    /** ���ܹ��� */
    private Cipher encryptCipher = null;

    /** ���ܹ��� */
    private Cipher decryptCipher = null;

    /**
     * ��byte����ת��Ϊ��ʾ16����ֵ���ַ����� �磺byte[]{8,18}ת��Ϊ��0813�� ��public static byte[]
     * hexStr2ByteArr(String strIn) ��Ϊ�����ת������
     * 
     * @param arrB
     *            ��Ҫת����byte����
     * @return ת������ַ���
     * @throws Exception
     *             �������������κ��쳣�������쳣ȫ���׳�
     */
    public static String byteArr2HexStr(byte[] arrB) {
        int iLen = arrB.length;
        // ÿ��byte�������ַ����ܱ�ʾ�������ַ����ĳ��������鳤�ȵ�����
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // �Ѹ���ת��Ϊ����
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // С��0F������Ҫ��ǰ�油0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * ����ʾ16����ֵ���ַ���ת��Ϊbyte���飬 ��public static String byteArr2HexStr(byte[] arrB)
     * ��Ϊ�����ת������
     * 
     * @param strIn
     *            ��Ҫת�����ַ���
     * @return ת�����byte����
     */
    public static byte[] hexStr2ByteArr(String strIn) {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        // �����ַ���ʾһ���ֽڣ������ֽ����鳤�����ַ������ȳ���2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    /**
     * Ĭ�Ϲ��췽����ʹ��Ĭ����Կ
     * @throws NoSuchPaddingException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * 
     */
    public DesUtils() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        this(strDefaultKey);
    }

    /**
     * ָ����Կ���췽��
     * 
     * @param strKey
     *            ָ������Կ
     * @throws NoSuchPaddingException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     */
    public DesUtils(String strKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Key key = getKey(strKey.getBytes());

        encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);

        decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    /**
     * �����ֽ�����
     * 
     * @param arrB
     *            ����ܵ��ֽ�����
     * @return ���ܺ���ֽ�����
     * @throws BadPaddingException 
     * @throws IllegalBlockSizeException 
     */
    public byte[] encrypt(byte[] arrB) throws IllegalBlockSizeException, BadPaddingException {
        return encryptCipher.doFinal(arrB);
    }

    /**
     * �����ַ���
     * 
     * @param strIn
     *            ����ܵ��ַ���
     * @return ���ܺ���ַ���
     * @throws BadPaddingException 
     * @throws IllegalBlockSizeException 
     */
    public String encrypt(String strIn) throws IllegalBlockSizeException, BadPaddingException {
        return byteArr2HexStr(encrypt(strIn.getBytes()));
    }

    /**
     * �����ֽ�����
     * 
     * @param arrB
     *            ����ܵ��ֽ�����
     * @return ���ܺ���ֽ�����
     * @throws BadPaddingException 
     * @throws IllegalBlockSizeException 
     */
    public byte[] decrypt(byte[] arrB) throws IllegalBlockSizeException, BadPaddingException {
        return decryptCipher.doFinal(arrB);
    }

    /**
     * �����ַ���
     * 
     * @param strIn
     *            ����ܵ��ַ���
     * @return ���ܺ���ַ���
     * @throws BadPaddingException 
     * @throws IllegalBlockSizeException 
     */
    public String decrypt(String strIn) throws IllegalBlockSizeException, BadPaddingException {
        return new String(decrypt(hexStr2ByteArr(strIn)));
    }

    /**
     * ��ָ���ַ���������Կ����Կ������ֽ����鳤��Ϊ8λ ����8λʱ���油0������8λֻȡǰ8λ
     * 
     * @param arrBTmp
     *            ���ɸ��ַ������ֽ�����
     * @return ���ɵ���Կ
     * @throws java.lang.Exception
     */
    private Key getKey(byte[] arrBTmp) {
        // ����һ���յ�8λ�ֽ����飨Ĭ��ֵΪ0��
        byte[] arrB = new byte[8];

        // ��ԭʼ�ֽ�����ת��Ϊ8λ
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        // ������Կ
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
        return key;
    }
}
