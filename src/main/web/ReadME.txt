安装过程如下:
1：把webroot目录拷贝至tomcat的网站路径下，打开webroot\web-inf\classes\jdbc.properties文件，修改DBUserName为数据库用户名,DBPassword为数据库密码。
2：打开网站，系统会自动跳转至安装界面，输入超级管理员帐号和密码以及邮箱，等待自动安装，安装失败的话，根据提示解决问题。
3：提示数据库创建失败，手动安装的时候，需要自行把data\install.sql或者install_mysql5.5+.sql在数据库中导入，然后再导入install_data.sql,再按照步骤2进行安装。


注：install.sql适用与mysql5.5之前的版本，install_mysql5.5+.sql适用于5.5之后的版本
