package com.neoniou.tools.utils;

/**
 * @author Neo.Zzj
 * @date 2021/2/15
 */
public class BiliCrcUtil {

    private static final int TABLE_SIZE = 256;

    private static final int BIT_SIZE = 8;

    private static final int CRC_POLY = 0xEDB88320;

    private static final int[] CRC_TABLE = new int[TABLE_SIZE];

    static {
        createTable();
    }

    public static String calculateOrigin(String str) {
        int[] index = new int[4];
        long temp = Long.parseLong(str, 16);
        int ht = (int) ~temp;
        int sNum, i, lastIndex;
        for (i = 1; i < 1001; i++) {
            if (ht == crc32(String.valueOf(i), false)) {
                return String.valueOf(i);
            }
        }
        for (i = 3; i >= 0; i--) {
            index[3 - i] = getCrcIndex((int) (ht >>> (i * 8)));
            sNum = CRC_TABLE[index[3 - i]];
            ht ^= sNum >>> ((3 - i) * 8);
        }
        String deepCheckData = "";
        for (i = 0; i < 1e6; i++) {
            lastIndex = crc32(String.valueOf(i), true);
            if (lastIndex == index[3]) {
                deepCheckData = deepCheck(i, index);
                if (deepCheckData != null) {
                    break;
                }
            }
        }
        return (i == 1e6) ? null : (i + "" + deepCheckData);
    }

    private static int crc32(String str, boolean returnIndex) {
        int crcStart = 0xFFFFFFFF;
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            index = (crcStart ^ str.charAt(i)) & 0xff;
            crcStart = (crcStart >>> 8) ^ CRC_TABLE[index];
        }
        return returnIndex ? index : crcStart;
    }

    private static int getCrcIndex(int num) {
        for (int i = 0; i < TABLE_SIZE; i++) {
            if (CRC_TABLE[i] >>> 24 == num) {
                return i;
            }
        }
        return -1;
    }

    private static String deepCheck(int i, int[] index) {
        int tc;
        String str = "";
        int hash = crc32(String.valueOf(i), false);

        tc = hash & 0xff ^ index[2];
        if (!(tc <= 57 && tc >= 48)) {
            return null;
        }

        str += tc - 48;
        hash = CRC_TABLE[index[2]] ^ (hash >>> 8);
        tc = hash & 0xff ^ index[1];
        if (!(tc <= 57 && tc >= 48)) {
            return null;
        }
        str += tc - 48;
        hash = CRC_TABLE[index[1]] ^ (hash >>> 8);
        tc = hash & 0xff ^ index[0];
        if (!(tc <= 57 && tc >= 48)) {
            return null;
        }
        str += tc - 48;
        hash = CRC_TABLE[index[0]] ^ (hash >>> 8);

        return str;
    }

    private static void createTable() {
        for (int i = 0; i < TABLE_SIZE; i++) {
            int crcReg = i;
            for (int j = 0; j < BIT_SIZE; ++j) {
                crcReg = (crcReg & 1) != 0 ? CRC_POLY ^ (crcReg >>> 1) : crcReg >>> 1;
            }
            CRC_TABLE[i] = crcReg;
        }
    }
}
