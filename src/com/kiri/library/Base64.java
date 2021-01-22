package com.kiri.library;

public class Base64 {

    private final static String _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    public static byte[] encode(String input) {
        StringBuffer output = new StringBuffer();
        int chr1, chr2, chr3, enc1 = 0, enc2 = 0, enc3, enc4;
        int i = 0;
        input = Base64._encode(input);
        while (i < input.length()) {
            chr1 = Base64._charAt(input, i++);
            chr2 = Base64._charAt(input, i++);
            chr3 = Base64._charAt(input, i++);
            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;
            if (chr2 == 0) {
                enc3 = 64;
                enc4 = 64;
            } else if (chr3 == 0) {
                enc4 = 64;
            }
            output.append(_keyStr.charAt(enc1));
            output.append(_keyStr.charAt(enc2));
            output.append(_keyStr.charAt(enc3));
            output.append(_keyStr.charAt(enc4));
        }
        return output.toString().getBytes();
    }

    public static byte[] decode(String input) {
        StringBuffer output = new StringBuffer();
        int chr1, chr2, chr3, enc1, enc2, enc3, enc4;
        int i = 0;
        input = input.replaceAll("[^A-Za-z0-9\\+\\/\\=]", "");
        while (i < input.length()) {
            enc1 = _keyStr.indexOf(input.charAt(i++));
            enc2 = _keyStr.indexOf(input.charAt(i++));
            enc3 = _keyStr.indexOf(input.charAt(i++));
            enc4 = _keyStr.indexOf(input.charAt(i++));
            chr1 = (enc1 << 2) | (enc2 >> 4);
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
            chr3 = ((enc3 & 3) << 6) | enc4;
            output.append((char) chr1);
            if (enc3 != 64) {
                output.append((char) chr2);
            }
            if (enc4 != 64) {
                output.append((char) chr3);
            }
        }
        return Base64._decode(output.toString()).getBytes();
    }

    public static byte[] encode(byte[] input) {
        return Base64.encode(new String(input));
    }

    public static byte[] decode(byte[] input) {
        return Base64.decode(new String(input));
    }

    public static String encodeToString(byte[] input) {
        return new String(Base64.encode(input));
    }

    public static String decodeToString(byte[] input) {
        return new String(Base64.decode(input));
    }

    @Deprecated
    public static String encodeToString(String input) {
        return Base64.encodeToString(input.getBytes());
    }

    @Deprecated
    public static String decodeToString(String input) {
        return Base64.decodeToString(input.getBytes());
    }

    private static String _encode(String string) {
        string = string.replace("\r\n", "\n");
        StringBuffer utftext = new StringBuffer();
        for (int i = 0, j = string.length(); i < j; i++) {
            int c = string.charAt(i);
            if (c < 128) {
                utftext.append((char) c);
            } else if ((c > 127) && (c < 2048)) {
                utftext.append((char) (c >> 6) | 192);
                utftext.append((char) (c & 63) | 128);
            } else {
                utftext.append((char) (c >> 12) | 224);
                utftext.append((char) ((c >> 6) & 63) | 128);
                utftext.append((char) (c & 63) | 128);
            }
        }
        return utftext.toString();
    }

    private static String _decode(String utftext) {
        StringBuffer string = new StringBuffer();
        int i = 0;
        int c, c1, c2, c3;
        while (i < utftext.length()) {
            c = utftext.charAt(i);
            if (c < 128) {
                string.append((char) c);
                i++;
            } else if ((c > 191) && (c < 224)) {
                c2 = utftext.charAt(++i);
                string.append((char) ((c & 31) << 6) | (c2 & 63));
                i++;
            } else {
                c2 = utftext.charAt(++i);
                c3 = utftext.charAt(++i);
                string.append((char) ((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                i++;
            }
        }
        return string.toString();
    }

    private static int _charAt(String string, int i) {
        try {
            return string.charAt(i);
        } catch (StringIndexOutOfBoundsException e) {
            return 0;
        }
    }
}
