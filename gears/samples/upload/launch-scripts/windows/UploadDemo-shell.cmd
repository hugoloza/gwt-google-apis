@if "%GWT_HOME%"=="" goto needGWTHome
@java -cp "%~dp0\..\..\src;%~dp0\..\..\bin;%GWT_HOME%\gwt-user.jar;%GWT_HOME%\gwt-dev-windows.jar;..\..\..\..\gwt-gears.jar" com.google.gwt.dev.GWTShell -out "%~dp0\..\..\www" %* com.google.gwt.gears.sample.upload.UploadDemo/UploadDemo.html
@exit /B %ERRORLEVEL%

:needGWTHome
@echo The environment variable GWT_HOME is not defined, it should point to a valid GWT installation.
@exit /B 1

