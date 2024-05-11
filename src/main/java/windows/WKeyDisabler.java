package windows;

import cli.annotations.Command;
import cli.annotations.Run;
import shell.OSDetection;

import java.nio.file.Path;

@Command(
    command="wkey-disabler",
    description="Disables the windows key (under MS-Windows)",
    isBackground = true
)
public class WKeyDisabler {

    @Run
    // Entry point for df
    public void execute(Path cwd) throws Exception {
        if (OSDetection.isWindows()) {
            SystemTrayIcon.ICON.load();
            DisablerService.SERVICE.disableWKey();
        } else {
            throw new Exception("Unsupported OS!");
        }
    }
}