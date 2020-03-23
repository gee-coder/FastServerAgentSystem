package com.hanger.model;

/**
 * @author hanger
 * 2020-01-16 16:46
 */
public class GUIModel {
    //ANSI表
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    //logo
    public static final String LOGO =
            "                         @@@@@@@@@@                  \n" +
                    "                      @@@@@@@@@@@@@@@@               \n" +
                    "                    @@@@@@@@@@@@@@@@@@@@             \n" +
                    "                   @@@@@@@@@@@@@@@@@@@@@@            \n" +
                    "                  @@@@@@@@@@    @@@@@@@@@@@@@@@@@    \n" +
                    "                  @@@@@@@@@      @@@@@@@@@@@@@@@@@@@ \n" +
                    "           @@@@@@@@@@@@@@@@      @@@@@@@@@@@@@@@@@@@@\n" +
                    "         @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@    @@@@@@\n" +
                    "         @@@@@  @@@@@@@@@@@@@@@@@@@@@@@@@@@    @@@@@@\n" +
                    "         @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n" +
                    "         @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ \n" +
                    "           @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@    \n" +
                    "                @           @@@         @@           \n" +
                    "               @            @@@           @@         \n" +
                    "              @             @@@             @@       \n" +
                    "             @              @@@              @@      \n" +
                    "             @              @@@               @@     \n" +
                    "             @              @@@           @@@@@@@@@@ \n" +
                    "             @              @@@          @@@@@@@@@@@@\n" +
                    "           @@@@@          @@@@@@@        @@@@@  @@@@@\n" +
                    "         @@@@@@@@@      @@@@@@@@@@@@     @@@@@@@@@@@@\n" +
                    "         @@@@ @@@@    @@@@@@@@@@@@@@@     @@@@@@@@@@ \n" +
                    "         @@@@@@@@@    @@@@@@@  @@@@@@@      @@@@@@   \n" +
                    "           @@@@@     @@@@@@@    @@@@@@               \n" +
                    "                      @@@@@@@@@@@@@@@@               \n" +
                    "                       @@@@@@@@@@@@@@                \n" +
                    "                        @@@@@@@@@@@@                 \n" +
                    "                           @@@@@@                    \n" +
                    "                                                     \n";

    //版本信息
    public static final String VERSION =
                    "            __     ___   ____   _____   ____                    \n" +
                    "           /__\\   / __) ( ___) ( )_( ) (_  _)     :: AGENT ::  \n" +
                    "          /(__)\\ ( (_-.  )__)  | | | |   )(                    \n" +
                    "         (__)(__) \\___/ (____) (_) (_)  (__)     v1.0.0.RELEASE\n" +
                    "                  Agent is not a substitute for processing      \n" +
                    "                                                                \n";

    //输入指示
    public static final String C_IN =
    "请输入/:__>";

    //菜单栏
    public static final String ITEM =
            "######### 输入序号执行对应程序 #########\n" +
                    " 1：查询当前所有映射\n" +
                    " 2：添加新映射\n" +
                    " 3：删除指定已有映射\n" +
                    " 4：退出程序（较慢）\n" +
                    "######### 输入完成后按回车开始 #########\n" +
                    C_IN;

    //提示
    public static final String TIP1 =
            "请输入要映射的服务所在局域网的IPV4地址与服务对应端口\n" +
                    "注意：IP与端口之间的冒号为英文冒号‘:’\n" +
                    "例如：127.0.0.1:8080\n" +
                    C_IN;

    //提示
    public static final String TIP2 =
            "请输入要删除的映射记录的key值\n" +
                    C_IN;

    //警告
    public static final String WARNING =
            "请输入正确的操作！ (●ˇ∀ˇ●)";

    //退出
    public static final String EXIT =
            "Bye (。・∀・)ノ";

}
