package com.face.utils;

import java.nio.ByteBuffer;
import java.util.Calendar;

import static java.lang.System.arraycopy;

public class BufferUtil {
    public static byte[] long2Bytes(long srcNum) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(srcNum);
        return buffer.array();
    }

    public static void putShort(byte b[], short s, int index) {
        b[index + 1] = (byte) (s >> 8);
        b[index + 0] = (byte) (s >> 0);
    }

    public static short getShort(byte[] b, int index) {
        return (short) (((b[index + 1] << 8) | b[index + 0] & 0xff));
    }

    public static void putInt(byte[] b, int x, int index) {
        b[index + 3] = (byte) (x >> 24);
        b[index + 2] = (byte) (x >> 16);
        b[index + 1] = (byte) (x >> 8);
        b[index + 0] = (byte) (x >> 0);
    }

    public static int getInt(byte[] bb, int index) {
        return (int) ((((bb[index + 3] & 0xff) << 24)
                     | ((bb[index + 2] & 0xff) << 16)
                     | ((bb[index + 1] & 0xff) << 8)
                     | ((bb[index + 0] & 0xff) << 0)));
    }

    public static void putLong(byte[] bb, long x, int index) {
        bb[index + 7] = (byte) (x >> 56);
        bb[index + 6] = (byte) (x >> 48);
        bb[index + 5] = (byte) (x >> 40);
        bb[index + 4] = (byte) (x >> 32);
        bb[index + 3] = (byte) (x >> 24);
        bb[index + 2] = (byte) (x >> 16);
        bb[index + 1] = (byte) (x >> 8);
        bb[index + 0] = (byte) (x >> 0);
    }


    public static long getLong(byte[] bb, int index) {
        return ((((long) bb[index + 7] & 0xff) << 56)
                | (((long) bb[index + 6] & 0xff) << 48)
                | (((long) bb[index + 5] & 0xff) << 40)
                | (((long) bb[index + 4] & 0xff) << 32)
                | (((long) bb[index + 3] & 0xff) << 24)
                | (((long) bb[index + 2] & 0xff) << 16)
                | (((long) bb[index + 1] & 0xff) << 8)
                | (((long) bb[index + 0] & 0xff) << 0));
    }

    public static byte[] getPartBytes(byte[] src, int startIdx, int len) {
        if (len <= 0) return null;
        if (startIdx <= 0) return null;
        if (src.length < (startIdx + len)) return null;

        byte[] result = new byte[len];

        for (int i = 0; i < len; i++) {
            result[i] = src[i+startIdx];
        }

        return result;
    }

    public static byte[] getBytes(byte[] src, int startIdx, int endIdx) {
        return getPartBytes(src, startIdx, endIdx-startIdx);
    }

    public byte[] append(byte[] bytes1, byte[] bytes2){
        byte[] bytes3 = new byte[bytes1.length+bytes2.length];
        arraycopy(bytes1, 0, bytes3, 0, bytes1.length);
        arraycopy(bytes2, 0, bytes3, bytes1.length, bytes2.length);

        return bytes3;
    }

    public static byte[] concat(byte[]... arrays) {
        byte length = 0;
        for (byte[] array : arrays) {
            length += array.length;
        }
        byte[] result = new byte[length];
        byte pos = 0;
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, result, pos, array.length);
            pos += array.length;
        }
        return result;
    }

    public static Calendar bytesToDateTime(byte[] bytes, int offset) {
        long timestamp = getLong(bytes, offset);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        return c;
    }

}
