package cn.soshare.me.YYTV;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = true;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "cn.soshare.me.YYTV", "cn.soshare.me.YYTV.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
		BA.handler.postDelayed(new WaitForLayout(), 5);

	}
	private static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "cn.soshare.me.YYTV", "cn.soshare.me.YYTV.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "cn.soshare.me.YYTV.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}

public anywheresoftware.b4a.keywords.Common __c = null;
public uk.co.martinpearman.b4a.vitamio.widget.MediaController _vitamiomediacontroller1 = null;
public uk.co.martinpearman.b4a.vitamio.widget.VideoView _vitamiovideoview1 = null;
public static String[][] _playlist = null;
public anywheresoftware.b4a.objects.ListViewWrapper _listview = null;
public static int _playposition = 0;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_ch = null;
public static long _time = 0L;
public static String _fileaddr = "";
public static int _int_listlength = 0;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_updatelist = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public cn.soshare.me.YYTV.ini_rw _ini_rw = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}
public static String  _activity_click() throws Exception{
boolean _b = false;
 //BA.debugLineNum = 101;BA.debugLine="Sub Activity_Click";
 //BA.debugLineNum = 102;BA.debugLine="listview.SetSelection(PlayPosition)";
mostCurrent._listview.SetSelection(_playposition);
 //BA.debugLineNum = 103;BA.debugLine="Dim b As Boolean";
_b = false;
 //BA.debugLineNum = 104;BA.debugLine="b = listview.Visible";
_b = mostCurrent._listview.getVisible();
 //BA.debugLineNum = 105;BA.debugLine="If b = True Then";
if (_b==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 106;BA.debugLine="listview.Visible = False";
mostCurrent._listview.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 107;BA.debugLine="Btn_UpdateList.Visible = False";
mostCurrent._btn_updatelist.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 109;BA.debugLine="listview.Visible = True";
mostCurrent._listview.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 110;BA.debugLine="Btn_UpdateList.Visible = True";
mostCurrent._btn_updatelist.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
return "";
}
public static String  _activity_create(boolean _firsttime) throws Exception{
int _i = 0;
String _buffer = "";
 //BA.debugLineNum = 36;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 39;BA.debugLine="If VitamioVideoView1.CheckVitamioLibs Then";
if (mostCurrent._vitamiovideoview1.CheckVitamioLibs(mostCurrent.activityBA)) { 
 //BA.debugLineNum = 41;BA.debugLine="VitamioVideoView1.Initialize(\"VitamioVideoView1\"";
mostCurrent._vitamiovideoview1.Initialize(mostCurrent.activityBA,"VitamioVideoView1");
 //BA.debugLineNum = 42;BA.debugLine="Activity.AddView(VitamioVideoView1, 0, 0, 100%x,";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._vitamiovideoview1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 44;BA.debugLine="VitamioMediaController1.Initialize(\"VitamioMedia";
mostCurrent._vitamiomediacontroller1.Initialize(mostCurrent.activityBA,"VitamioMediaController1");
 //BA.debugLineNum = 45;BA.debugLine="VitamioVideoView1.SetVideoChroma( 0 )";
mostCurrent._vitamiovideoview1.SetVideoChroma((int) (0));
 //BA.debugLineNum = 46;BA.debugLine="VitamioVideoView1.SetVideoQuality(VitamioVideoVi";
mostCurrent._vitamiovideoview1.SetVideoQuality(mostCurrent._vitamiovideoview1.VIDEO_QUALITY_HIGH);
 };
 //BA.debugLineNum = 49;BA.debugLine="listview.Initialize(\"listview\")";
mostCurrent._listview.Initialize(mostCurrent.activityBA,"listview");
 //BA.debugLineNum = 50;BA.debugLine="Activity.AddView(listview,0%x,0%y,30%x,100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._listview.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (0),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 51;BA.debugLine="listview.Visible = False";
mostCurrent._listview.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 52;BA.debugLine="convCSV(\"PlayList.csv\",PlayList)";
_convcsv("PlayList.csv",mostCurrent._playlist);
 //BA.debugLineNum = 53;BA.debugLine="For i = 0 To PlayList.Length - 1";
{
final int step26 = 1;
final int limit26 = (int) (mostCurrent._playlist.length-1);
for (_i = (int) (0); (step26 > 0 && _i <= limit26) || (step26 < 0 && _i >= limit26); _i = ((int)(0 + _i + step26))) {
 //BA.debugLineNum = 54;BA.debugLine="Int_ListLength = i";
_int_listlength = _i;
 //BA.debugLineNum = 55;BA.debugLine="If PlayList(i,0) = \"\" Then Exit";
if ((mostCurrent._playlist[_i][(int) (0)]).equals("")) { 
if (true) break;};
 //BA.debugLineNum = 56;BA.debugLine="listview.AddSingleLine(PlayList(i,0))";
mostCurrent._listview.AddSingleLine(mostCurrent._playlist[_i][(int) (0)]);
 }
};
 //BA.debugLineNum = 59;BA.debugLine="lbl_ch.Initialize(\"\")";
mostCurrent._lbl_ch.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 60;BA.debugLine="Activity.AddView(lbl_ch,0%x,0%y,100%x,10%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._lbl_ch.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (0),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (0),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 61;BA.debugLine="lbl_ch.Gravity = Gravity.RIGHT";
mostCurrent._lbl_ch.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 62;BA.debugLine="Lbl_AutoSize(lbl_ch,0.5)";
_lbl_autosize(mostCurrent._lbl_ch,(float) (0.5));
 //BA.debugLineNum = 64;BA.debugLine="Dim buffer As String";
_buffer = "";
 //BA.debugLineNum = 65;BA.debugLine="buffer = INI_RW.Readini(File.DirDefaultExternal,\"";
_buffer = mostCurrent._ini_rw._readini(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"Config.ini","PlayList","position");
 //BA.debugLineNum = 66;BA.debugLine="If (buffer = \"\") Then";
if (((_buffer).equals(""))) { 
 //BA.debugLineNum = 67;BA.debugLine="PlayPosition = 0";
_playposition = (int) (0);
 }else {
 //BA.debugLineNum = 69;BA.debugLine="PlayPosition = buffer";
_playposition = (int)(Double.parseDouble(_buffer));
 };
 //BA.debugLineNum = 72;BA.debugLine="buffer = INI_RW.Readini(File.DirDefaultExternal,\"";
_buffer = mostCurrent._ini_rw._readini(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"Config.ini","Source","FileAddr");
 //BA.debugLineNum = 73;BA.debugLine="If (buffer = \"\") Then";
if (((_buffer).equals(""))) { 
 //BA.debugLineNum = 74;BA.debugLine="FileAddr = \"http://www.soshare.cn/app/YYTV/playl";
mostCurrent._fileaddr = "http://www.soshare.cn/app/YYTV/playlist.csv";
 //BA.debugLineNum = 75;BA.debugLine="INI_RW.Writeini(File.DirDefaultExternal,\"Config.";
mostCurrent._ini_rw._writeini(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"Config.ini","Source","FileAddr",mostCurrent._fileaddr);
 }else {
 //BA.debugLineNum = 77;BA.debugLine="FileAddr = buffer";
mostCurrent._fileaddr = _buffer;
 };
 //BA.debugLineNum = 79;BA.debugLine="YYTVPlay";
_yytvplay();
 //BA.debugLineNum = 81;BA.debugLine="Btn_UpdateList.Initialize(\"Btn_UpdateList\")";
mostCurrent._btn_updatelist.Initialize(mostCurrent.activityBA,"Btn_UpdateList");
 //BA.debugLineNum = 82;BA.debugLine="Activity.AddView(Btn_UpdateList,90%x,90%y,10%x,10";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._btn_updatelist.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 83;BA.debugLine="Btn_UpdateList.Text = \"UpdateList\"";
mostCurrent._btn_updatelist.setText((Object)("UpdateList"));
 //BA.debugLineNum = 84;BA.debugLine="Btn_UpdateList.Visible = False";
mostCurrent._btn_updatelist.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 85;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 122;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 123;BA.debugLine="Select KeyCode";
switch (BA.switchObjectToInt(_keycode,anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK,anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_DPAD_UP,anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_DPAD_DOWN,anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_MENU,anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_DPAD_CENTER)) {
case 0:
 //BA.debugLineNum = 125;BA.debugLine="If DateTime.Now-time>2000 Then";
if (anywheresoftware.b4a.keywords.Common.DateTime.getNow()-_time>2000) { 
 //BA.debugLineNum = 126;BA.debugLine="time=DateTime.Now";
_time = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 127;BA.debugLine="ToastMessageShow(\"再按一次返回退出\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("再按一次返回退出",anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 129;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 };
 //BA.debugLineNum = 131;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 break;
case 1:
 //BA.debugLineNum = 134;BA.debugLine="If PlayPosition = 0 Then";
if (_playposition==0) { 
 //BA.debugLineNum = 135;BA.debugLine="Exit";
if (true) break;
 }else {
 //BA.debugLineNum = 137;BA.debugLine="PlayPosition = PlayPosition - 1";
_playposition = (int) (_playposition-1);
 };
 //BA.debugLineNum = 139;BA.debugLine="YYTVPlay";
_yytvplay();
 break;
case 2:
 //BA.debugLineNum = 142;BA.debugLine="If PlayPosition = Int_ListLength - 1 Then";
if (_playposition==_int_listlength-1) { 
 //BA.debugLineNum = 143;BA.debugLine="Exit";
if (true) break;
 }else {
 //BA.debugLineNum = 145;BA.debugLine="PlayPosition = PlayPosition + 1";
_playposition = (int) (_playposition+1);
 //BA.debugLineNum = 146;BA.debugLine="If PlayList(PlayPosition,1) = \"\" Then PlayPosi";
if ((mostCurrent._playlist[_playposition][(int) (1)]).equals("")) { 
_playposition = (int) (_playposition-1);};
 };
 //BA.debugLineNum = 148;BA.debugLine="YYTVPlay";
_yytvplay();
 break;
case 3:
 //BA.debugLineNum = 151;BA.debugLine="PlayListDownLoad";
_playlistdownload();
 break;
case 4:
 break;
}
;
 //BA.debugLineNum = 155;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 118;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 120;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 114;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 116;BA.debugLine="End Sub";
return "";
}
public static String  _btn_updatelist_click() throws Exception{
 //BA.debugLineNum = 95;BA.debugLine="Sub Btn_UpdateList_Click";
 //BA.debugLineNum = 96;BA.debugLine="Btn_UpdateList.Visible = False";
mostCurrent._btn_updatelist.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 97;BA.debugLine="listview.Visible = False";
mostCurrent._listview.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 98;BA.debugLine="PlayListDownLoad";
_playlistdownload();
 //BA.debugLineNum = 99;BA.debugLine="End Sub";
return "";
}
public static String  _convcsv(String _csv,String[][] _arr) throws Exception{
anywheresoftware.b4a.objects.StringUtils _su = null;
anywheresoftware.b4a.objects.collections.List _list1 = null;
int _i = 0;
String[] _scol = null;
int _j = 0;
 //BA.debugLineNum = 204;BA.debugLine="Sub convCSV(csv As String,Arr(,) As String)";
 //BA.debugLineNum = 205;BA.debugLine="Dim su As StringUtils";
_su = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 206;BA.debugLine="Dim list1 As List";
_list1 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 207;BA.debugLine="If File.Exists(File.DirDefaultExternal,csv) = Tru";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),_csv)==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 208;BA.debugLine="list1 = su.LoadCSV(File.DirDefaultExternal, csv,";
_list1 = _su.LoadCSV(anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),_csv,BA.ObjectToChar(","));
 }else {
 //BA.debugLineNum = 210;BA.debugLine="list1 = su.LoadCSV(File.DirAssets, csv, \",\")";
_list1 = _su.LoadCSV(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_csv,BA.ObjectToChar(","));
 };
 //BA.debugLineNum = 212;BA.debugLine="For i = 0 To list1.Size-1";
{
final int step149 = 1;
final int limit149 = (int) (_list1.getSize()-1);
for (_i = (int) (0); (step149 > 0 && _i <= limit149) || (step149 < 0 && _i >= limit149); _i = ((int)(0 + _i + step149))) {
 //BA.debugLineNum = 213;BA.debugLine="Dim sCol() As String";
_scol = new String[(int) (0)];
java.util.Arrays.fill(_scol,"");
 //BA.debugLineNum = 214;BA.debugLine="sCol = list1.Get(i)";
_scol = (String[])(_list1.Get(_i));
 //BA.debugLineNum = 215;BA.debugLine="For j = 0 To sCol.Length-1";
{
final int step152 = 1;
final int limit152 = (int) (_scol.length-1);
for (_j = (int) (0); (step152 > 0 && _j <= limit152) || (step152 < 0 && _j >= limit152); _j = ((int)(0 + _j + step152))) {
 //BA.debugLineNum = 216;BA.debugLine="Arr(i,j) =  sCol(j)";
_arr[_i][_j] = _scol[_j];
 }
};
 }
};
 //BA.debugLineNum = 219;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 24;BA.debugLine="Dim VitamioMediaController1 As Vitamio_MediaContr";
mostCurrent._vitamiomediacontroller1 = new uk.co.martinpearman.b4a.vitamio.widget.MediaController();
 //BA.debugLineNum = 25;BA.debugLine="Dim VitamioVideoView1 As Vitamio_VideoView";
mostCurrent._vitamiovideoview1 = new uk.co.martinpearman.b4a.vitamio.widget.VideoView();
 //BA.debugLineNum = 26;BA.debugLine="Dim PlayList(200,3) As String";
mostCurrent._playlist = new String[(int) (200)][];
{
int d0 = mostCurrent._playlist.length;
int d1 = (int) (3);
for (int i0 = 0;i0 < d0;i0++) {
mostCurrent._playlist[i0] = new String[d1];
java.util.Arrays.fill(mostCurrent._playlist[i0],"");
}
}
;
 //BA.debugLineNum = 27;BA.debugLine="Dim listview As ListView";
mostCurrent._listview = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim PlayPosition As Int";
_playposition = 0;
 //BA.debugLineNum = 29;BA.debugLine="Dim lbl_ch As Label";
mostCurrent._lbl_ch = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim time As Long = 0";
_time = (long) (0);
 //BA.debugLineNum = 31;BA.debugLine="Dim FileAddr As String";
mostCurrent._fileaddr = "";
 //BA.debugLineNum = 32;BA.debugLine="Dim Int_ListLength As Int";
_int_listlength = 0;
 //BA.debugLineNum = 33;BA.debugLine="Dim Btn_UpdateList As Button";
mostCurrent._btn_updatelist = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
 //BA.debugLineNum = 239;BA.debugLine="Sub JobDone (Job As HttpJob)";
 //BA.debugLineNum = 240;BA.debugLine="If Job.Success = True Then";
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 241;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 242;BA.debugLine="out = File.OpenOutput(File.DirDefaultExternal,\"p";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"playlist.csv",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 243;BA.debugLine="File.Copy2(Job.GetInputStream, out)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_job._getinputstream().getObject()),(java.io.OutputStream)(_out.getObject()));
 //BA.debugLineNum = 244;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 245;BA.debugLine="ToastMessageShow(\"DownSucc,please restart!\",True";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("DownSucc,please restart!",anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 247;BA.debugLine="ToastMessageShow(\"DownFail!\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("DownFail!",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 249;BA.debugLine="End Sub";
return "";
}
public static float  _lbl_autosize(anywheresoftware.b4a.objects.LabelWrapper _view,float _factor) throws Exception{
anywheresoftware.b4a.objects.StringUtils _su = null;
float _size = 0f;
 //BA.debugLineNum = 221;BA.debugLine="Sub Lbl_AutoSize(view As Label,factor As Float) As";
 //BA.debugLineNum = 222;BA.debugLine="Dim su As StringUtils";
_su = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 223;BA.debugLine="Dim size As Float";
_size = 0f;
 //BA.debugLineNum = 224;BA.debugLine="view.Text = \"HMI\"";
_view.setText((Object)("HMI"));
 //BA.debugLineNum = 225;BA.debugLine="For size = 2 To 300";
{
final double step161 = 1;
final double limit161 = (float) (300);
for (_size = (float) (2); (step161 > 0 && _size <= limit161) || (step161 < 0 && _size >= limit161); _size = ((float)(0 + _size + step161))) {
 //BA.debugLineNum = 226;BA.debugLine="view.TextSize = size";
_view.setTextSize(_size);
 //BA.debugLineNum = 227;BA.debugLine="If su.MeasureMultilineTextHeight(view,view.Text)";
if (_su.MeasureMultilineTextHeight((android.widget.TextView)(_view.getObject()),_view.getText())>_view.getHeight()) { 
if (true) break;};
 }
};
 //BA.debugLineNum = 229;BA.debugLine="view.TextSize = (size-0.5) * factor";
_view.setTextSize((float) ((_size-0.5)*_factor));
 //BA.debugLineNum = 230;BA.debugLine="Return	(size-0.5) * factor";
if (true) return (float) ((_size-0.5)*_factor);
 //BA.debugLineNum = 231;BA.debugLine="End Sub";
return 0f;
}
public static String  _listview_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 87;BA.debugLine="Sub listview_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 88;BA.debugLine="If PlayList(Position,1) <> \"\" Then";
if ((mostCurrent._playlist[_position][(int) (1)]).equals("") == false) { 
 //BA.debugLineNum = 89;BA.debugLine="PlayPosition = Position";
_playposition = _position;
 //BA.debugLineNum = 90;BA.debugLine="YYTVPlay";
_yytvplay();
 //BA.debugLineNum = 91;BA.debugLine="Btn_UpdateList.Visible = False";
mostCurrent._btn_updatelist.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 93;BA.debugLine="End Sub";
return "";
}
public static String  _playlistdownload() throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _job1 = null;
 //BA.debugLineNum = 233;BA.debugLine="Sub PlayListDownLoad";
 //BA.debugLineNum = 234;BA.debugLine="Dim job1 As HttpJob";
_job1 = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 235;BA.debugLine="job1.Initialize(\"Job\", Me)";
_job1._initialize(processBA,"Job",main.getObject());
 //BA.debugLineNum = 236;BA.debugLine="job1.Download(FileAddr)";
_job1._download(mostCurrent._fileaddr);
 //BA.debugLineNum = 237;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        anywheresoftware.b4a.samples.httputils2.httputils2service._process_globals();
main._process_globals();
ini_rw._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return "";
}
public static String  _vitamiovideoview1_buffering(int _percent) throws Exception{
 //BA.debugLineNum = 166;BA.debugLine="Sub VitamioVideoView1_Buffering(Percent As Int)";
 //BA.debugLineNum = 167;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 168;BA.debugLine="lbl_ch.Text = PlayList(PlayPosition,0) & Percent";
mostCurrent._lbl_ch.setText((Object)(mostCurrent._playlist[_playposition][(int) (0)]+BA.NumberToString(_percent)+"%"));
 //BA.debugLineNum = 170;BA.debugLine="End Sub";
return "";
}
public static String  _vitamiovideoview1_complete() throws Exception{
 //BA.debugLineNum = 172;BA.debugLine="Sub VitamioVideoView1_Complete";
 //BA.debugLineNum = 174;BA.debugLine="End Sub";
return "";
}
public static boolean  _vitamiovideoview1_error(int _media_error) throws Exception{
 //BA.debugLineNum = 176;BA.debugLine="Sub VitamioVideoView1_Error(MEDIA_ERROR As Int) As";
 //BA.debugLineNum = 179;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 180;BA.debugLine="ToastMessageShow(\"无信号\",True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("无信号",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 181;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 182;BA.debugLine="End Sub";
return false;
}
public static boolean  _vitamiovideoview1_info(int _what,int _extra) throws Exception{
 //BA.debugLineNum = 184;BA.debugLine="Sub VitamioVideoView1_Info(What As Int, Extra As I";
 //BA.debugLineNum = 189;BA.debugLine="Return False 	'	indicates that this Sub has NOT h";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 190;BA.debugLine="End Sub";
return false;
}
public static String  _vitamiovideoview1_prepared() throws Exception{
 //BA.debugLineNum = 192;BA.debugLine="Sub VitamioVideoView1_Prepared";
 //BA.debugLineNum = 194;BA.debugLine="End Sub";
return "";
}
public static String  _vitamiovideoview1_seekcomplete() throws Exception{
 //BA.debugLineNum = 196;BA.debugLine="Sub VitamioVideoView1_SeekComplete";
 //BA.debugLineNum = 198;BA.debugLine="End Sub";
return "";
}
public static String  _vitamiovideoview1_textsubtitleupdated(String _subtext) throws Exception{
 //BA.debugLineNum = 200;BA.debugLine="Sub VitamioVideoView1_TextSubtitleUpdated(SubText";
 //BA.debugLineNum = 202;BA.debugLine="End Sub";
return "";
}
public static String  _yytvplay() throws Exception{
 //BA.debugLineNum = 157;BA.debugLine="Sub YYTVPlay";
 //BA.debugLineNum = 158;BA.debugLine="ProgressDialogShow(PlayList(PlayPosition,0))";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,mostCurrent._playlist[_playposition][(int) (0)]);
 //BA.debugLineNum = 159;BA.debugLine="lbl_ch.Text = PlayList(PlayPosition,0)";
mostCurrent._lbl_ch.setText((Object)(mostCurrent._playlist[_playposition][(int) (0)]));
 //BA.debugLineNum = 160;BA.debugLine="VitamioVideoView1.SetVideoPath(PlayList(PlayPosit";
mostCurrent._vitamiovideoview1.SetVideoPath(mostCurrent._playlist[_playposition][(int) (1)]);
 //BA.debugLineNum = 161;BA.debugLine="listview.Visible = False";
mostCurrent._listview.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 162;BA.debugLine="INI_RW.Writeini(File.DirDefaultExternal,\"Config.i";
mostCurrent._ini_rw._writeini(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"Config.ini","PlayList","position",BA.NumberToString(_playposition));
 //BA.debugLineNum = 163;BA.debugLine="VitamioVideoView1.SetVideoLayout(3,0)";
mostCurrent._vitamiovideoview1.SetVideoLayout((int) (3),(float) (0));
 //BA.debugLineNum = 164;BA.debugLine="End Sub";
return "";
}
}
