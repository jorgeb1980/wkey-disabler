package windows;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

public enum DisablerService {

    SERVICE;

    private WinUser.HHOOK hhk;
    private WinUser.LowLevelKeyboardProc keyboardHook;
    private User32 lib;
    private Thread thread;

    public void disableWKey() {
        thread = new Thread(() -> {
            lib = User32.INSTANCE;
            WinDef.HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
            var keyboardHook = new WinUser.LowLevelKeyboardProc() {
                public WinDef.LRESULT callback(int nCode, WinDef.WPARAM wParam, WinUser.KBDLLHOOKSTRUCT info) {
                    if (nCode >= 0) {
                        switch (info.vkCode) {
                            case 0x5B:
                            case 0x5C:
                                return new WinDef.LRESULT(1);
                            default: //do nothing
                        }
                    }
                    return lib.CallNextHookEx(hhk, nCode, wParam, new WinDef.LPARAM(info.getPointer().getLong(0)));
                }
            };
            hhk = lib.SetWindowsHookEx(13, keyboardHook, hMod, 0);

            // Should not expect this to return from GetMessage - thus the System.exit
            int result;
            WinUser.MSG msg = new WinUser.MSG();
            while ((result = lib.GetMessage(msg, null, 0, 0)) != 0) {
                if (result == -1) {
                    break;
                } else {
                    lib.TranslateMessage(msg);
                    lib.DispatchMessage(msg);
                }
            }
        });
        thread.start();
    }

    public void enableWKey() {
        lib.UnhookWindowsHookEx(hhk);
        // A bit abrupt - however, it's what we have here
        System.exit(0);
    }
}
