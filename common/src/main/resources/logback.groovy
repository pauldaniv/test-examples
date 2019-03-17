import ch.qos.logback.classic.AsyncAppender
import org.springframework.boot.logging.logback.ColorConverter
import org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter
import org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter

scan("30 seconds")

conversionRule 'clr', ColorConverter
conversionRule 'wex', WhitespaceThrowableProxyConverter
conversionRule 'wEx', ExtendedWhitespaceThrowableProxyConverter

def LOG_PATH = "logs"
def LOG_ARCHIVE = "${LOG_PATH}/archive"
def PID = "%property{PID}"
def LOG_EXCEPTION_CONVERSION_WORD = "%wex"
def LOG_LEVEL_PATTERN = "%6p"
def LOG_DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS"

def CONSOLE_LOG_PATTERN =
        "%clr(%d{${LOG_DATE_FORMAT_PATTERN}}){faint} " +
                "%clr(${LOG_LEVEL_PATTERN}) " +
                "%clr(${PID}){magenta} " +
                "%clr(---){faint} %clr([%15.15t]){faint} " +
                "%clr(%-40.40logger{39}){cyan} %clr(:){faint} " +
                "%m%n${LOG_EXCEPTION_CONVERSION_WORD}"


def FILE_LOG_PATTERN =
        "%d{${LOG_DATE_FORMAT_PATTERN}} " +
                "${LOG_LEVEL_PATTERN} " +
                "${PID} " +
                "--- [%15.15t] " +
                "%-40.40logger{39} : " +
                "%m%n${LOG_EXCEPTION_CONVERSION_WORD}"


appender("Console-Appender", ConsoleAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = CONSOLE_LOG_PATTERN
  }
}

appender("File-Appender", FileAppender) {
  file = "${LOG_PATH}/logfile.log"
  encoder(PatternLayoutEncoder) {
    pattern = FILE_LOG_PATTERN
    outputPatternAsHeader = true
  }
}

appender("RollingFile-Appender", RollingFileAppender) {

  file = "${LOG_PATH}/rollingfile.log"
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "${LOG_ARCHIVE}/rollingfile.log%d{yyyy-MM-dd}.log"
    maxHistory = 30
    totalSizeCap = "1KB"
  }

  encoder(PatternLayoutEncoder) {
    pattern = "%msg%n"
  }
}

appender("Async-Appender", AsyncAppender) {
  appenderRef("RollingFile-Appender")
}

logger("guru.springframework.blog.logbackgroovy", INFO, ["Console-Appender", "File-Appender", "Async-Appender"], false)
logger("com.paul.store", DEBUG, ["Console-Appender"], false)

root(INFO, ["Console-Appender"])
