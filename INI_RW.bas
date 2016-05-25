Type=StaticCode
Version=5.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
' Code module
' Subs in this code module will be accessible from all modules.
' ###################################################################################
' Functions to Read and Write ini-file from Activity

' ini.writeini(File.DirDefaultExternal,"test.ini","Section 1","TestKey1","Sunday")
' ini.writeini(File.DirDefaultExternal,"test.ini","Section 1","TestKey2","Monday")
' ini.writeini(File.DirDefaultExternal,"test.ini","Section 2","TestKey1","Tuesday")
' ini.writeini(File.DirDefaultExternal,"test.ini","Section 2","TestKey2","Wednesday")
' Creates this
' 
' (File  test.ini)
' [Section 1]
' TestKey1=Sunday
' TestKey2=Monday
' [Section 2]
' TestKey1=Tuesday
' TestKey2=Wednesday
' 
' To read 1 of above Keys into variable 'str'
' str = ini.readini(File.DirDefaultExternal,"test.ini","Section 1","TestKey1")
' str will be set to Sunday
' #####################################################################################
Sub Process_Globals 
'These global variables will be declared once when the application starts. 
'These variables can be accessed from all modules. 
	Dim Reader As TextReader 
	Dim section As String 
	Dim str As String 
	Dim str2 As String
	Dim l As List
	Dim l2 As List 
	l.Initialize
	l2.Initialize
End Sub

Sub Loadini(fpath As String, filename As String) As Boolean
	l.Clear
	l2.Clear
	section = "" 
	If File.Exists(fpath, filename) Then
		Reader.Initialize(File.OpenInput(fpath, filename)) 
		Dim line As String 
		line = Reader.ReadLine
		Do While line <> Null  
			If line.StartsWith("[") Then 
				section = line.Replace("[","")
				section = section.Replace("]","")
			Else 
				If section <> "" Then 
					l.Add(section & "=" & line) 
				End If 
			End If 
			line = Reader.ReadLine
		Loop 
		Reader.Close 
	Else
		Return False
	End If 
	l.Sort(True) 
	section = "" 
	Return True
End Sub

Sub splitit(inp As String) 
str = "" 
str2 = "" 
If inp.IndexOf("=") > 0 Then 
	str = inp.SubString2(0,inp.IndexOf("=")) 
	str2 = inp.SubString(inp.IndexOf("=")+ 1) 
End If 
End Sub

Sub Readini(fpath As String,filename As String,sections As String,keys As String) As String
	If Loadini(fpath,filename) = False Then Return ""
	str = "" 
	str2 = sections & "=" & keys & "="
	For i = 0 To (l.Size - 1)
		str = l.Get(i)
		If str.StartsWith(str2) Then
			splitit(str)
			splitit(str2)
		DoEvents
		Return str2 
		End If 
	Next
	Return ""
End Sub

Sub Writeini(fpath As String, filename As String, sections As String,keys As String,value As String) As Boolean
	If Writeini2(fpath, filename, sections,keys,value) Then Return True
	Return Writeini2(fpath, filename, sections,keys,value)
End Sub

Sub Writeini2(fpath As String, filename As String, Sections As String,keys As String,value As String) As Boolean
	Loadini(fpath,filename)	
	
	Dim replace As Int	 
	replace = 0 
	str = "" 
	str2 = Sections & "=" & keys & "="
	For i = 0 To (l.Size - 1)
		str = l.Get(i)
		If str.StartsWith(str2) Then
			l.RemoveAt(i)
			l.Add(str2 & value)
			replace = 1 
		End If 
	Next
	If replace = 0 Then 
		l.Add(str2 & value)
	Else
		replace = 0 
	End If 
	l.Sort(True) 
	Dim Writer As TextWriter 
	Writer.Initialize(File.OpenOutput(fpath, filename, False)) 
	Dim laststr As String 
	For i = 0 To l.Size - 1 
		splitit(l.Get(i))
		If str <> laststr Then 
			l2.Add("[" & str & "]") 
			l2.Add(str2)
		Else
			l2.Add(str2)
		End If 
		laststr = str
	Next
	Writer.WriteList(l2)
	Writer.Close 
	Return True
End Sub
