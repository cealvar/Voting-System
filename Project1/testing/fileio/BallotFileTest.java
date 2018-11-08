package fileio;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class BallotFileTest {

    private static BallotFile bf;
    private static File ballot_file;

    @BeforeEach
    void setup() {
        ballot_file = new File(FileSystems.getDefault()
                .getPath("testing", "simple_opl_ballot_file.csv")
                .toString());
        try {
            bf = new BallotFile(ballot_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void BallotFileConstructorDoesNotThrowExceptions() {
        assertDoesNotThrow(() -> {
            BallotFile _bf = new BallotFile(ballot_file);
        });
    }

    @Test
    void BallotFileConstructorThrowsIOException() {
        assertThrows(IOException.class, () -> {
            String file_path = FileSystems.getDefault().getPath("src", "doesnotexist.csv").toString();
            File doesNotExist = new File(file_path);
            BallotFile _bf = new BallotFile(doesNotExist);
        });
    }

    @Test
    void getFilenameSuccess() {
        assertEquals("simple_opl_ballot_file.csv", bf.getFilename());
    }

    @Test
    void getFilenameFail() {
        assertNotEquals("simples_opl_ballot_file.csv", bf.getFilename());
    }

    @Test
    void getLine3SimpleOPLSuccess() {
        assertEquals("[Pike,D], [Foster,D],[Deutsch,R], [Borg,R], [Jones,R],[Smith,I]",
                bf.getLine(3));
    }

    @Test
    void getLine3SimpleOPLFail() {
        assertNotEquals("[Pike,R], [Foster,D],[Deutsch,R], [Borg,R], [Jones,R],[Smith,I]",
                bf.getLine(3));
    }

    @Test
    void getLineLastLine() {
        assertEquals(",1,,,,", bf.getLine(bf.getNumLines()));
    }

    @Test
    void getLineThrowsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            bf.getLine(bf.getNumLines() + 1);
        });
    }

    @Test
    void getElectionTypeOPLSuccess() {
        assertEquals("OPL", bf.getElectionType());
    }

    @Test
    void getElectionTypeOPLFail() {
        assertNotEquals("IR", bf.getElectionType());
    }
}