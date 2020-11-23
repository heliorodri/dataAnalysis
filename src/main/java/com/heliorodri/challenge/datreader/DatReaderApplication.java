package com.heliorodri.challenge.datreader;

import com.heliorodri.challenge.datreader.config.AppConfig;
import com.heliorodri.challenge.datreader.reader.DatProcessor;
import com.heliorodri.challenge.datreader.utils.Infos;
import com.heliorodri.challenge.datreader.utils.WriteLog;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

@SpringBootApplication
public class DatReaderApplication {

    public static void main(String[] args) {
        watchDir();
    }

    public static void watchDir() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        DatProcessor datProcessor = (DatProcessor) applicationContext.getBean("datProcessor");

        Path path = Paths.get(Infos.INPUT_DIR.getValue());

        FileSystem fs = path.getFileSystem();
        try (WatchService service = fs.newWatchService()) {
            path.register(service, ENTRY_CREATE);

            WatchKey key = null;
            do {
                key = service.take();

                WatchEvent.Kind<?> kind = null;
                for (WatchEvent<?> watchEvent : key.pollEvents()) {
                    kind = watchEvent.kind();

                    if (ENTRY_CREATE == kind) {
                        Path newPath = ((WatchEvent<Path>) watchEvent).context();
                        if ((newPath.getFileName().toString().contains(".dat")) && !newPath.getFileName().toString().contains(".done")) {
                            datProcessor.analize(newPath.getFileName().toString());
                        }
                    }
                }

            } while (key.reset());
        } catch (InterruptedException | IOException e) {
            WriteLog.log(e, DatReaderApplication.class, "watchDir");
        }
    }

}
