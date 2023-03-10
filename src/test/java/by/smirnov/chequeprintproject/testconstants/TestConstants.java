package by.smirnov.chequeprintproject.testconstants;

import java.sql.Timestamp;

public interface TestConstants {

    String CHEQUE_BEGINNING =
            "----------------------------------------------------------\n" +
                    "                       CASH RECEIPT                       \n" +
                    "                    DrumsticksStore#1                     \n" +
                    "             Minsk, Herearound Str., 111-222              \n" +
                    "                  tel. +375(11)222-33-44                  \n" +
                    "\n" +
                    "CASHIER: 1001                               DATE: ";

    String CHEQUE_END =
            "==========================================================\n" +
                    "QTY DESCRIPTION                            PRICE  TOTAL \n" +
                    "\n" +
                    " 4  Vic Firth drumsticks 2B                 14,00$  56,00$\n" +
                    " 5  Vic Firth drumsticks 2BN                14,00$  70,00$\n" +
                    "==========================================================\n" +
                    "SUM                                                   126$\n" +
                    "PROMO DISCOUNT                                          0$\n" +
                    "CARD DISCOUNT                                         6,3$\n" +
                    "TAXABLE TOT.                                        99,75$\n" +
                    "VAT20%                                              19,95$\n" +
                    "\n" +
                    "TOTAL                                               119,7$\n" +
                    "==========================================================\n" +
                    "\n" +
                    "                *** Place your ad here ***                \n" +
                    "\n" +
                    "---------------------------------------------------------";

    Timestamp DEFAULT_TIMESTAMP = Timestamp.valueOf("2022-01-01 00:00:00");

    Long TEST_ID = 1L;
}
