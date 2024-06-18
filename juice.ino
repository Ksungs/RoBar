#include <SoftwareSerial.h>
#include <Wire.h>
#include <LiquidCrystal_I2C.h>

LiquidCrystal_I2C lcd(0x27, 16, 2);
SoftwareSerial BT(2, 3);
char cmd[2];
int timer;
String menu;
int tn;
int mn;
int A1A = 6;  // 모터드라이버 설정
int A1B = 7;
int B1A = 8;
int B1B = 9;
int C1A = 10;
int C1B = 11;
int D1A = 4;
int D1B = 5;  // 모터드라이버 설정
int ir = 0;
int start = 0;  // 매뉴얼을 최초 1회만 전송하기 위한 변수

void setup() {
    lcd.begin();
    lcd.clear();
    lcd.backlight();
    pinMode(12, INPUT);
    pinMode(13, OUTPUT);  // 적외선센서 및 led
    Serial.begin(9600);
    BT.begin(9600);
    pinMode(A1A, OUTPUT);  // 모터드라이버 설정
    pinMode(A1B, OUTPUT);
    pinMode(B1A, OUTPUT);
    pinMode(B1B, OUTPUT);
    pinMode(C1A, OUTPUT);
    pinMode(C1B, OUTPUT);
    pinMode(D1A, OUTPUT);
    pinMode(D1B, OUTPUT);  // 모터드라이버 설정
}

void loop() {
    if (start == 0) {  // 매뉴얼을 최초 1회만 전송
        Serial.println("start");
        BT.println("Please input table number and menu number");
        BT.println("God Father:1 Screw Driver:2 Jackcoke:3");
        BT.println("ex)23");
        BT.println(" ");
        start = 1;
    }  // 매뉴얼을 최초 1회만 전송
    ir = irDetect(12);  // 적외선 감지
    Serial.println(ir);
    if (!ir) {  // 적외선이 감지되었을 때만 음료 제조
        delay(100);
        if (BT.available()) {  // 블루투스 신호를 받으면 음료 제조
            cmd[0] = BT.read();  // 블루투스 신호의 자료형이 char이기 때문에 2번에 나누어
            cmd[1] = BT.read();  // cmd라는 두 자리 리스트에 저장

            mn = cmd[1];  // 메뉴 번호 저장
            if (cmd[0] == '1') tn = 1;
            if (cmd[0] == '2') tn = 2;
            if (cmd[0] == '3') tn = 3;  // 테이블 번호를 int형으로 저장

            if (mn == '1' || mn == '2' || mn == '3' || mn == '4') {  // 메뉴번호에 따라 음료 제조
                switch (mn) {  // 음료제조에 필요한 함수 실행
                    case '1':
                        GodFather();
                        break;
                    case '2':
                        ScrewDriver();
                        break;
                    case '3':
                        Jackcoke();
                        break;
                    case '4':
                        GodMother();
                        break;
                }  // 음료제조에 필요한 함수 실행
                for (int i = 0; i < tn + 1; i++) {
                    tone(13, 38000, 100);
                    delay(1000);
                }  // 음료 제조 후 (테이블 번호+1)회 적외선 신호를 보냄
                lcd.clear();
                ir = 1;
            }
            start = 0;  // 매뉴얼을 다시 전송하기 위한 변수 설정
        }
    }
}

int irDetect(int irReceiverPin) {
    int ir = digitalRead(irReceiverPin);
    delay(1);
    return ir;
}  // 적외선 감지

/* 각 메뉴별 음료 제조 함수 */
void GodFather() {
    timer = 7;
    menu = "God Father";
    digitalWrite(A1A, HIGH);
    digitalWrite(A1B, LOW);
    digitalWrite(D1A, HIGH);
    digitalWrite(D1B, LOW);
    delay(1000);
    llcd();
    delay(1000);
    llcd();
    delay(1000);
    llcd();
    digitalWrite(A1A, LOW);
    digitalWrite(A1B, LOW);
    delay(1000);
    llcd();
    delay(1000);
    llcd();
    delay(1000);
    llcd();
    delay(1000);
    llcd();
    delay(1000);
    digitalWrite(D1A, LOW);
    digitalWrite(D1B, LOW);
    delay(1000);
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("End Thank you!");
    delay(2000);
}

void ScrewDriver() {
    timer = 7;
    menu = "Screw Driver";
    llcd();
    digitalWrite(B1A, HIGH);
    digitalWrite(B1B, LOW);
    digitalWrite(C1A, HIGH);
    digitalWrite(C1B, LOW);
    delay(1000);
    llcd();
    delay(1000);
    llcd();
    delay(1000);
    llcd();
    digitalWrite(B1A, LOW);
    digitalWrite(B1B, LOW);
    delay(1000);
    llcd();
    delay(1000);
    llcd();
    delay(1000);
    llcd();
    delay(1000);
    llcd();
    delay(1000);
    digitalWrite(C1A, LOW);
    digitalWrite(C1B, LOW);
    delay(1000);
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("End Thank you!");
    delay(2000);
}

void Jackcoke() {
    timer = 7;
    menu = "Jackcoke";
    llcd();
    digitalWrite(A1A, HIGH);
    digitalWrite(A1B, LOW);
    digitalWrite(B1A, HIGH);
    digitalWrite(B1B, LOW);
    delay(1000);
    llcd();
    delay(1000);
    llcd();
    delay(1000);
    llcd();
    digitalWrite(A1A, LOW);
    digitalWrite(A1B, LOW);
    delay(1000);
    llcd();
    delay(1000);
    llcd();
    delay(1000);
    llcd();
    delay(1000);
    llcd();
    delay(1000);
    digitalWrite(B1A, LOW);
    digitalWrite(B1B, LOW);
    delay(1000);
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("End Thank you!");
    delay(2000);
}

void GodMother() {
    timer = 7;
    menu = "God Mother";
    llcd();
    digitalWrite(C1A, HIGH);
    digitalWrite(C1B, LOW);
    digitalWrite(D1A, HIGH);
    digitalWrite(D1B, LOW);
    delay(1000);
    llcd();
    delay(1000);
    llcd();
    delay(1000);
    llcd();
    digitalWrite(C1A, LOW);
    digitalWrite(C1B, LOW);
    delay(1000);
    llcd();
    delay(1000);
    llcd();
    delay(1000);
    llcd();
    delay(1000);
    llcd();
    delay(1000);
    digitalWrite(D1A, LOW);
    digitalWrite(D1B, LOW);
    delay(1000);
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("End Thank you!");
    delay(2000);
}
/* 각 메뉴별 음료 제조 함수 */

void llcd() {
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print(menu);
    lcd.setCursor(0, 1);
    lcd.print(timer);
    timer -= 1;
}  // lcd에 메뉴명과 남은 시간을 출력하기 위한 함수

void turnOffOutputs() {
    digitalWrite(A1A, LOW);
    digitalWrite(A1B, LOW);
    digitalWrite(B1A, LOW);
    digitalWrite(B1B, LOW);
    digitalWrite(C1A, LOW);
    digitalWrite(C1B, LOW);
    digitalWrite(D1A, LOW);
    digitalWrite(D1B, LOW);
}  // 워터펌프를 전부 off시키는 함수
