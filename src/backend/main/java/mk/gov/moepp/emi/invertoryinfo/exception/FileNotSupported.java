package mk.gov.moepp.emi.invertoryinfo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.FileInputStream;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class FileNotSupported extends RuntimeException {

    public FileNotSupported(String message){
        super(message);
    }

}
