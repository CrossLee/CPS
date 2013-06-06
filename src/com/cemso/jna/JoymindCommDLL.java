/*
 * Created on 2012-6-5
 *
 * JoymindCommDLL.java
 *
 * Copyright (C) 2012 by Withiter Software & Technology Services (Shanghai) Limited.
 * All rights reserved. Withiter Software & Technology Services (Shanghai) Limited 
 * claims copyright in this computer program as an unpublished work. Claim of copyright 
 * does not imply waiver of other rights.
 *
 * NOTICE OF PROPRIETARY RIGHTS
 *
 * This program is a confidential trade secret and the property of 
 * Withiter Software & Technology Services (Shanghai) Limited. Use, examination, 
 * reproduction, disassembly, decompiling, transfer and/or disclosure to others of 
 * all or any part of this software program are strictly prohibited except by express 
 * written agreement with Withiter Software & Technology Services (Shanghai) Limited.
 */
/*
 * ---------------------------------------------------------------------------------
 * Modification History
 * Date               Author                     Version     Description
 * 2012-6-5       CrossLee                    1.0         New
 * ---------------------------------------------------------------------------------
 */
/**
 * 
 */
package com.cemso.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

/**
 * @author gl65293
 */
public class JoymindCommDLL {

    public interface JoymindCommDLLLib extends Library {

        String dllPath = (Thread.currentThread().getContextClassLoader().getResource("tools/").toString() + "JoymindComm.dll").substring(6);
        JoymindCommDLLLib INSTANCE = (JoymindCommDLLLib) Native.loadLibrary((Platform.isWindows() ? dllPath : "c"), JoymindCommDLLLib.class);

        /* System Configuration */
        /**
         * ������ʾ������ 
         * @param ip        IP��ַ
         * @param width     ��ʾ�����ȣ���λ������
         * @param height    ��ʾ���߶ȣ���λ������
         * @return          0������ʧ�ܣ�1�������ɹ�
         */
        public int SetScreenPara(String ip, int width, int height);

        /**
         * ����������
         * @param ip        IP��ַ
         * @param power     ������״̬��0��������1������
         * @return          0������ʧ�ܣ�1�������ɹ�
         */
        public int SetPower(String ip, int power);

        /**
         * ��������
         * @param ip        IP��ַ
         * @return          0������ʧ�ܣ�1�������ɹ�
         */
        public int ClearScreen(String ip);

        /**
         * У��ʱ��
         * @param ip        IP��ַ
         * @return          0������ʧ�ܣ�1�������ɹ�
         */
        public int AdjustTime(String ip);

        /**
         * ���ÿ������������
         * @param ip            IP��ַ
         * @param ipAddress     ��IP��ַ
         * @param getway        ����
         * @param subnetmask    ��������
         * @return              0������ʧ�ܣ�1�������ɹ�
         */
        public int SetNetwork(String ip, String ipAddress, String getway, String subnetmask);

        /**
         * �༭���Ŀ���ݺ󣬷�����ʾ����
         * @param ip        IP��ַ
         * @return          0������ʧ�ܣ�1�������ɹ�
         */
        public int SendDisplayData(String ip);

        /**
         * �����̬�� ��ʱ�ļ�
         */
        public void ResetDLL();

        /**
         * �ϴ�ý���ļ�
         * @param ip        IP��ַ
         * @param fileName  �ļ�·��
         * @return          0������ʧ�ܣ�1�������ɹ�
         */
        public int AddMediaFile(String ip, String fileName);

        /**
         * �������ý���ļ�
         * @param ip        IP��ַ
         * @return          0������ʧ�ܣ�1�������ɹ�
         */
        public int ClearMediaFile(String ip);

        /* System Configuration */

        /* Display Configuration */
        /**
         * ��ӽ�Ŀ
         * @param jno       ��Ŀ�ţ� >=1 ��
         * @param playTime  ����ʱ�� ����λ���룩
         *                  �� = 0�� ��ȴ��������
         *                  �� > 0�� �򲥷�ָ��ʱ��
         * @param bgFile    ����ͼƬ���ƣ���Ϊ0�����ޱ���ͼƬ�����ݲ�֧�֡�
         * @return          0������ʧ�ܣ�1�������ɹ�
         */
        public int AddProgram(int jno, int playTime, String bgFile);

        /**
         * ����ı�����
         * @param jno           ��Ŀ�� (>=1)
         * @param qno           ����� (>=1)
         * @param left          �������ϽǶ���x���꣬��λ������
         * @param top           �������ϽǶ���y���꣬��λ������
         * @param width         �����ȣ���λ������
         * @param height        ����߶�
         * @param fontColor     ������ɫ
         * @param fontName      ����������֧�����塿
         * @param fontSize      �����
         * @param fontBold      �����ϸ(0�����Ӵ�;1���Ӵ�)
         * @param fontItalic    ����б��(0����б��;1��б��) ���ݲ�֧�֡�
         * @param fontUnder     �����»���(0����;1����) ���ݲ�֧�֡�
         * @param line          �Զ�����(0�����Զ�;1���Զ�)
         * @param hAlign        ˮƽ����(1������룻2�����У�3���Ҷ���)
         * @param vAlign        ��ֱ����(1���϶��룻2�����У�3���¶���)
         * @param text          ��ʾ�ַ���
         * @param type          ��ʾ�ؼ�
         * @param speed         ��ʾ�ٶ�
         * @param delay         ͣ��ʱ��
         * @return              0������ʧ�ܣ�1�������ɹ�
         */
        public int AddTextArea(int jno, int qno, int left, int top, int width, int height, int fontColor, String fontName, int fontSize, int fontBold, int fontItalic, int fontUnder, int line,
                int hAlign, int vAlign, String text, int type, int speed, int delay);

        /**
         * �������ʱ������
         * @param jno           ��Ŀ�� (>=1)
         * @param qno           ����� (>=1)
         * @param left          �������ϽǶ���x���꣬��λ������
         * @param top           �������ϽǶ���y���꣬��λ������
         * @param width         �����ȣ���λ������
         * @param height        ����߶�
         * @param fontColor     ������ɫ
         * @param fontName      ������
         * @param fontSize      �����
         * @param fontBold      �����ϸ(0�����Ӵ�;1���Ӵ�)
         * @param fontItalic    ����б��(0����б��;1��б��) ���ݲ�֧�֡�
         * @param fontUnder     �����»���(0����;1����) ���ݲ�֧�֡�
         * @param mode          ʱ���ʽ
                                1��ȫ����ʾ
                                2��������
                                3������
                                4��ʱ����
                                5��ʱ��
                                6������
                                7����
                                8����
                                9����
                                10��ʱ
                                11����
                                12����

         * @param format        ��ʾģʽ 
                                1��2008��08��08�� 08ʱ08��08��
                                2��2008-08-08 08:08:08
                                3��08/08/2008 08:08:08

         * @return              0������ʧ�ܣ�1�������ɹ�
         */
        public int AddDClockArea(int jno, int qno, int left, int top, int widht, int height, int fontColor, String fontName, int fontSize, int fontBold, int fontItalic, int fontUnder, int mode,
                int format);

        /**
         * ����ļ�����Ŀǰ֧�ֵ��ļ���ʽ�У� 
            1��ͼƬ��ʽ��bmp��jpg��png
            2�����ݲ�֧�֡��ı���ʽ��doc��rtf��doc
            3)������ʽ��gif     

         * @param jno           ��Ŀ�� (>=1)
         * @param qno           ����� (>=1)
         * @param left          �������ϽǶ���x���꣬��λ������
         * @param top           �������ϽǶ���y���꣬��λ������
         * @param width         �����ȣ���λ������
         * @param height        ����߶�
         * @return              0������ʧ�ܣ�1�������ɹ�
         */
        public int AddFileArea(int jno, int qno, int left, int top, int width, int height);

        /**
         * ����ļ���ͼ��������
                                 ���ļ���ʽΪgifʱ���ؼ����͡������ٶȡ�ͣ��ʱ���ʹ������ֵ����λ���ᰴgifԭʼЧ�����š�
         * @param jno           ��Ŀ�� (>=1)
         * @param qno           ����� (>=1)
         * @param mno           �ļ��� (>=1)
         * @param fileName      �ļ�·��
         * @param width         �ļ���ʾ���(ĿǰΪ����Ŀ��) ���ɺ��ԡ�
         * @param height        �ļ���ʾ�߶�(ĿǰΪ����ĸ߶�) ���ɺ��ԡ�
         * @param type          �ؼ����ͣ���Ч������¼��
         * @param speed         �����ٶ�
         * @param delay         ͣ��ʱ��
         * @return              0������ʧ�ܣ�1�������ɹ�
         */
        public int AddFile2Area(int jno, int qno, int mno, String fileName, int width, int height, int type, int speed, int delay);

        /**
         * �����Ƶ����ÿ����Ŀ���1����
            1.Ŀǰ֧�ֵ��ļ���ʽ�У� H.264��25M��������MPEG1/2��40M��������MPEG4/Divx/Xvid��35M��������VC-1��30M��������RMVB8/9/10��30MB��������H.263��40M������ 
            2.��Ƶ����������������������ӡ�
            3.�����ٶȣ�50MB�ļ���Ҫ30��  

         * @param jno           ��Ŀ�� (>=1)
         * @param qno           ����� (>=1)
         * @param left          �������ϽǶ���x���꣬��λ������
         * @param top           �������ϽǶ���y���꣬��λ������
         * @param width         �����ȣ���λ������
         * @param height        ����߶�
         * @param fileName      �ļ�����
         * @return              0������ʧ�ܣ�1�������ɹ�
         */
        public int AddVideoArea(int jno, int qno, int left, int top, int width, int height, String fileName);
        /* Display Configuration */
        
        /**
         * ��¼1���ؼ��б�
            0����ҳ����ֹ��
            1������
            2������
            3������
            4������
         */
        
        public int GetRGB( int r, int g, int b);
        
        
        
        
        /**
         * the interface need from my side
         */
        /**
         * @param ip            
         * @param configFile    
         * @return              0������ʧ�ܣ�1�������ɹ�
         */
        public int SetTimerPower(String ip, String configFile);
        
        /**
         * @param ip
         * @return the file path
         */
        public String GetScreenShot(String ip);
        
        /**
         * @param macAddress
         * @return the device information
         */
        public String AddDevice(String macAddress);
        
        /**
         * @param ip
         * @return              0������ʧ�ܣ�1�������ɹ�
         */
        public int ResetDevice(String ip);
        
        /**
         * ���ͽ�Ŀѹ����
         * @param ip
         * @param fileName
         * @return              0������ʧ�ܣ�1�������ɹ�
         */
        public int PushProgram(String ip, String fileName);
        
        /**
         * [CTRL_INFO]   	 

			PNO 		=�����ţ� ���ݿ�����Ӳ������ֵ
			VERSION 	=��ϵͳ����汾�ţ�
			WIDTH		=������
			HEIGHT		=�����ߣ�
			STORAGE		=���洢ģʽ��
							1��FLASH�洢
							2��RAM�洢
							3��SD���洢
			IP			=��IP��ַ��
			GATEWAY		=�����أ�
			SUBNETMASK	=���������룩
			MACADDRESS	=��MAC��ַ��
							��ʽ��xx-xx-xx-xx-xx-xx
			STATUS		=���豸����״̬��
							0 : ����״̬
							1 : ����״̬
			TIME		=���豸��ǰʱ�䣩
							Eg: 2010/7/23 19:16:15
			FREEDISK	=��ʣ��洢�ռ��С,��λBYTE�� 
			PRGTOTAL	=���豸��Ŀ������
			PRGINDEX	=����ǰ���Ž�Ŀ��ţ�
			BOOT_TIME	=���豸����ʱ�䣩
							Eg: 2010/7/23 19:16:15
							
         * @param ip			IP��ַ
         * @param fileName		���ɵ��ļ�����
         * @return				0������ʧ�ܣ�1�������ɹ�
         */
        public int GetSysInfo(String ip, String fileName);
        
        /**
         * @param jno			��Ŀ�� ��>=1��
         * @param type			��ʱ���ͣ�0���޶�ʱ����1��ѭ�����ţ���2��ָ�����ţ�
         * @param startYear		��ʼ���ڣ��꣩
         * @param startMonth	��ʼ���ڣ��£�
         * @param startDay		��ʼ���ڣ��գ�
         * @param startHour		��ʼʱ�䣨ʱ��
         * @param startMinute	��ʼʱ�䣨�֣�
         * @param startSecond	��ʼʱ�䣨�룩
         * @param endYear		�������ڣ��꣩
         * @param endMonth		�������ڣ��£�
         * @param endDay		�������ڣ��գ�
         * @param endHour		����ʱ�䣨ʱ��
         * @param endMinute		����ʱ�䣨�֣�
         * @param endSecond		����ʱ�䣨�룩
         * @param monday		����һ��ʾ ��0������ʾ��1����ʾ��
         * @param tuesday		���ڶ���ʾ ��0������ʾ��1����ʾ��
         * @param wednesday		��������ʾ ��0������ʾ��1����ʾ��
         * @param thursday		��������ʾ ��0������ʾ��1����ʾ��
         * @param friday		��������ʾ ��0������ʾ��1����ʾ��
         * @param saturday		��������ʾ ��0������ʾ��1����ʾ��
         * @param sunday		��������ʾ ��0������ʾ��1����ʾ��
         * @return				0������ʧ�ܣ�1�������ɹ�
         * 
         * ע��
         * 1������ʱ����Ϊ�޶�ʱ������������������Ϊ0�����������Ž�Ŀ��
         * 2������ʱ����Ϊѭ�����ţ����տ�ʼ���ڵ��������ڣ���������ʾ�����õ����ڣ�Ȼ��ÿ����ݿ�ʼʱ�䵽����ʱ�䲥�Ž�Ŀ��
         * 3������ʱ����Ϊָ�����ţ����տ�ʼ���ڵĿ�ʼʱ��һֱ���������ڵĽ���ʱ�䲥�Ž�Ŀ
         */
        public int SetProgTimer(int jno, int type, 
        		int startYear, int startMonth, int startDay, int startHour, int startMinute, int startSecond,
        		int endYear, int endMonth, int endDay, int endHour, int endMinute, int endSecond,
        		int monday, int tuesday, int wednesday, int thursday, int friday, int saturday, int sunday);

    }
}














