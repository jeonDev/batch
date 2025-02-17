DELETE FROM RETRY;
INSERT INTO RETRY(count, check_yn) VALUES (1, 'N');
INSERT INTO RETRY(count, check_yn) VALUES (2, 'N');
INSERT INTO SKIP(check_yn) VALUES (null);
INSERT INTO SKIP(check_yn) VALUES (null);