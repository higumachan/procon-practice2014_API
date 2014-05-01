#coding: utf-8

import urllib
import urllib2
import json

BASE_URL = "http://procon2014-practice.oknct-ict.org"

"""
param:
    id (int): 問題のID

return:
    問題のストリーム
"""
def get_problem(id):
    f = urllib2.urlopen(BASE_URL + "/problem/ppm/%d" % id);
    return f;

"""
param:
    id (int)        : 問題のID
    username (str)  : ユーザネーム
    passwd (str)    : パスワード 
    answer_text (str): 回答のテキストデータ

return:
    レスポンスのディクショナリ
"""
def send_answer(id, username, passwd, answer_text):
    send_data = {
        "username": username,
        "passwd": passwd,
        "answer_text":answer_text,
    };
    send_data_enc = urllib.urlencode(send_data);
    f = urllib2.urlopen(BASE_URL + "/solve/json/%d" % id, send_data_enc);

    return json.loads(f.read());

if __name__ == '__main__':
    f = get_problem(1);
    print f.read();
    d = send_answer(1, "testkpcp", "1234", "2\r\n11\r\n21\r\nURDDLLURRDLLUURDDLUUD\r\n11\r\n40\r\nURDLURLDLURDRDLURUDLURDLLRDLUURRDLLURRDL\r\n");
    print d

