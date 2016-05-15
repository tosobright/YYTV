package cn.soshare.me.YYTV;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class ini_rw {
private static ini_rw mostCurrent = new ini_rw();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _reader = null;
public static String _section = "";
public static String _str = "";
public static String _str2 = "";
public static anywheresoftware.b4a.objects.collections.List _l = null;
public static anywheresoftware.b4a.objects.collections.List _l2 = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public cn.soshare.me.YYTV.main _main = null;
public static boolean  _loadini(anywheresoftware.b4a.BA _ba,String _fpath,String _filename) throws Exception{
String _line = "";
 //BA.debugLineNum = 37;BA.debugLine="Sub Loadini(fpath As String, filename As String) A";
 //BA.debugLineNum = 38;BA.debugLine="l.Clear";
_l.Clear();
 //BA.debugLineNum = 39;BA.debugLine="l2.Clear";
_l2.Clear();
 //BA.debugLineNum = 40;BA.debugLine="section = \"\"";
_section = "";
 //BA.debugLineNum = 41;BA.debugLine="If File.Exists(fpath, filename) Then";
if (anywheresoftware.b4a.keywords.Common.File.Exists(_fpath,_filename)) { 
 //BA.debugLineNum = 42;BA.debugLine="Reader.Initialize(File.OpenInput(fpath, filename";
_reader.Initialize((java.io.InputStream)(anywheresoftware.b4a.keywords.Common.File.OpenInput(_fpath,_filename).getObject()));
 //BA.debugLineNum = 43;BA.debugLine="Dim line As String";
_line = "";
 //BA.debugLineNum = 44;BA.debugLine="line = Reader.ReadLine";
_line = _reader.ReadLine();
 //BA.debugLineNum = 45;BA.debugLine="Do While line <> Null";
while (_line!= null) {
 //BA.debugLineNum = 46;BA.debugLine="If line.StartsWith(\"[\") Then";
if (_line.startsWith("[")) { 
 //BA.debugLineNum = 47;BA.debugLine="section = line.Replace(\"[\",\"\")";
_section = _line.replace("[","");
 //BA.debugLineNum = 48;BA.debugLine="section = section.Replace(\"]\",\"\")";
_section = _section.replace("]","");
 }else {
 //BA.debugLineNum = 50;BA.debugLine="If section <> \"\" Then";
if ((_section).equals("") == false) { 
 //BA.debugLineNum = 51;BA.debugLine="l.Add(section & \"=\" & line)";
_l.Add((Object)(_section+"="+_line));
 };
 };
 //BA.debugLineNum = 54;BA.debugLine="line = Reader.ReadLine";
_line = _reader.ReadLine();
 }
;
 //BA.debugLineNum = 56;BA.debugLine="Reader.Close";
_reader.Close();
 }else {
 //BA.debugLineNum = 58;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 60;BA.debugLine="l.Sort(True)";
_l.Sort(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 61;BA.debugLine="section = \"\"";
_section = "";
 //BA.debugLineNum = 62;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 63;BA.debugLine="End Sub";
return false;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 24;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 27;BA.debugLine="Dim Reader As TextReader";
_reader = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim section As String";
_section = "";
 //BA.debugLineNum = 29;BA.debugLine="Dim str As String";
_str = "";
 //BA.debugLineNum = 30;BA.debugLine="Dim str2 As String";
_str2 = "";
 //BA.debugLineNum = 31;BA.debugLine="Dim l As List";
_l = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 32;BA.debugLine="Dim l2 As List";
_l2 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 33;BA.debugLine="l.Initialize";
_l.Initialize();
 //BA.debugLineNum = 34;BA.debugLine="l2.Initialize";
_l2.Initialize();
 //BA.debugLineNum = 35;BA.debugLine="End Sub";
return "";
}
public static String  _readini(anywheresoftware.b4a.BA _ba,String _fpath,String _filename,String _sections,String _keys) throws Exception{
int _i = 0;
 //BA.debugLineNum = 74;BA.debugLine="Sub Readini(fpath As String,filename As String,sec";
 //BA.debugLineNum = 75;BA.debugLine="If Loadini(fpath,filename) = False Then Return \"\"";
if (_loadini(_ba,_fpath,_filename)==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 76;BA.debugLine="str = \"\"";
_str = "";
 //BA.debugLineNum = 77;BA.debugLine="str2 = sections & \"=\" & keys & \"=\"";
_str2 = _sections+"="+_keys+"=";
 //BA.debugLineNum = 78;BA.debugLine="For i = 0 To (l.Size - 1)";
{
final int step49 = 1;
final int limit49 = (int) ((_l.getSize()-1));
for (_i = (int) (0); (step49 > 0 && _i <= limit49) || (step49 < 0 && _i >= limit49); _i = ((int)(0 + _i + step49))) {
 //BA.debugLineNum = 79;BA.debugLine="str = l.Get(i)";
_str = BA.ObjectToString(_l.Get(_i));
 //BA.debugLineNum = 80;BA.debugLine="If str.StartsWith(str2) Then";
if (_str.startsWith(_str2)) { 
 //BA.debugLineNum = 81;BA.debugLine="splitit(str)";
_splitit(_ba,_str);
 //BA.debugLineNum = 82;BA.debugLine="splitit(str2)";
_splitit(_ba,_str2);
 //BA.debugLineNum = 83;BA.debugLine="DoEvents";
anywheresoftware.b4a.keywords.Common.DoEvents();
 //BA.debugLineNum = 84;BA.debugLine="Return str2";
if (true) return _str2;
 };
 }
};
 //BA.debugLineNum = 87;BA.debugLine="Return \"\"";
if (true) return "";
 //BA.debugLineNum = 88;BA.debugLine="End Sub";
return "";
}
public static String  _splitit(anywheresoftware.b4a.BA _ba,String _inp) throws Exception{
 //BA.debugLineNum = 65;BA.debugLine="Sub splitit(inp As String)";
 //BA.debugLineNum = 66;BA.debugLine="str = \"\"";
_str = "";
 //BA.debugLineNum = 67;BA.debugLine="str2 = \"\"";
_str2 = "";
 //BA.debugLineNum = 68;BA.debugLine="If inp.IndexOf(\"=\") > 0 Then";
if (_inp.indexOf("=")>0) { 
 //BA.debugLineNum = 69;BA.debugLine="str = inp.SubString2(0,inp.IndexOf(\"=\"))";
_str = _inp.substring((int) (0),_inp.indexOf("="));
 //BA.debugLineNum = 70;BA.debugLine="str2 = inp.SubString(inp.IndexOf(\"=\")+ 1)";
_str2 = _inp.substring((int) (_inp.indexOf("=")+1));
 };
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public static boolean  _writeini(anywheresoftware.b4a.BA _ba,String _fpath,String _filename,String _sections,String _keys,String _value) throws Exception{
 //BA.debugLineNum = 90;BA.debugLine="Sub Writeini(fpath As String, filename As String,";
 //BA.debugLineNum = 91;BA.debugLine="If Writeini2(fpath, filename, sections,keys,value";
if (_writeini2(_ba,_fpath,_filename,_sections,_keys,_value)) { 
if (true) return anywheresoftware.b4a.keywords.Common.True;};
 //BA.debugLineNum = 92;BA.debugLine="Return Writeini2(fpath, filename, sections,keys,v";
if (true) return _writeini2(_ba,_fpath,_filename,_sections,_keys,_value);
 //BA.debugLineNum = 93;BA.debugLine="End Sub";
return false;
}
public static boolean  _writeini2(anywheresoftware.b4a.BA _ba,String _fpath,String _filename,String _sections,String _keys,String _value) throws Exception{
int _replace = 0;
int _i = 0;
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _writer = null;
String _laststr = "";
 //BA.debugLineNum = 95;BA.debugLine="Sub Writeini2(fpath As String, filename As String,";
 //BA.debugLineNum = 96;BA.debugLine="Loadini(fpath,filename)";
_loadini(_ba,_fpath,_filename);
 //BA.debugLineNum = 98;BA.debugLine="Dim replace As Int";
_replace = 0;
 //BA.debugLineNum = 99;BA.debugLine="replace = 0";
_replace = (int) (0);
 //BA.debugLineNum = 100;BA.debugLine="str = \"\"";
_str = "";
 //BA.debugLineNum = 101;BA.debugLine="str2 = Sections & \"=\" & keys & \"=\"";
_str2 = _sections+"="+_keys+"=";
 //BA.debugLineNum = 102;BA.debugLine="For i = 0 To (l.Size - 1)";
{
final int step70 = 1;
final int limit70 = (int) ((_l.getSize()-1));
for (_i = (int) (0); (step70 > 0 && _i <= limit70) || (step70 < 0 && _i >= limit70); _i = ((int)(0 + _i + step70))) {
 //BA.debugLineNum = 103;BA.debugLine="str = l.Get(i)";
_str = BA.ObjectToString(_l.Get(_i));
 //BA.debugLineNum = 104;BA.debugLine="If str.StartsWith(str2) Then";
if (_str.startsWith(_str2)) { 
 //BA.debugLineNum = 105;BA.debugLine="l.RemoveAt(i)";
_l.RemoveAt(_i);
 //BA.debugLineNum = 106;BA.debugLine="l.Add(str2 & value)";
_l.Add((Object)(_str2+_value));
 //BA.debugLineNum = 107;BA.debugLine="replace = 1";
_replace = (int) (1);
 };
 }
};
 //BA.debugLineNum = 110;BA.debugLine="If replace = 0 Then";
if (_replace==0) { 
 //BA.debugLineNum = 111;BA.debugLine="l.Add(str2 & value)";
_l.Add((Object)(_str2+_value));
 }else {
 //BA.debugLineNum = 113;BA.debugLine="replace = 0";
_replace = (int) (0);
 };
 //BA.debugLineNum = 115;BA.debugLine="l.Sort(True)";
_l.Sort(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 116;BA.debugLine="Dim Writer As TextWriter";
_writer = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 117;BA.debugLine="Writer.Initialize(File.OpenOutput(fpath, filename";
_writer.Initialize((java.io.OutputStream)(anywheresoftware.b4a.keywords.Common.File.OpenOutput(_fpath,_filename,anywheresoftware.b4a.keywords.Common.False).getObject()));
 //BA.debugLineNum = 118;BA.debugLine="Dim laststr As String";
_laststr = "";
 //BA.debugLineNum = 119;BA.debugLine="For i = 0 To l.Size - 1";
{
final int step87 = 1;
final int limit87 = (int) (_l.getSize()-1);
for (_i = (int) (0); (step87 > 0 && _i <= limit87) || (step87 < 0 && _i >= limit87); _i = ((int)(0 + _i + step87))) {
 //BA.debugLineNum = 120;BA.debugLine="splitit(l.Get(i))";
_splitit(_ba,BA.ObjectToString(_l.Get(_i)));
 //BA.debugLineNum = 121;BA.debugLine="If str <> laststr Then";
if ((_str).equals(_laststr) == false) { 
 //BA.debugLineNum = 122;BA.debugLine="l2.Add(\"[\" & str & \"]\")";
_l2.Add((Object)("["+_str+"]"));
 //BA.debugLineNum = 123;BA.debugLine="l2.Add(str2)";
_l2.Add((Object)(_str2));
 }else {
 //BA.debugLineNum = 125;BA.debugLine="l2.Add(str2)";
_l2.Add((Object)(_str2));
 };
 //BA.debugLineNum = 127;BA.debugLine="laststr = str";
_laststr = _str;
 }
};
 //BA.debugLineNum = 129;BA.debugLine="Writer.WriteList(l2)";
_writer.WriteList(_l2);
 //BA.debugLineNum = 130;BA.debugLine="Writer.Close";
_writer.Close();
 //BA.debugLineNum = 131;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 132;BA.debugLine="End Sub";
return false;
}
}
