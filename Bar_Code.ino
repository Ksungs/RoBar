#include <SoftwareSerial.h>
#include <Wire.h>
#include <LiquidCrystal_I2C.h>

SoftwareSerial BT(2, 3); 
char cmd[2];
int timer;
String menu;
int tn; int mn;
int A1A = 6;  // 모터 드라이버 설정
int A1B = 7;
int B1A = 8;
int B1B = 9;
int C1A = 10;
int C1B = 11;
int D1A = 4;
int D1B = 5;
int start = 0;  // 매뉴얼을 최초 1회만 전송하기 위한 변수

void setup() {
  Serial.begin(9600); 
  BT.begin(9600);

  pinMode(A1A, OUTPUT);   // 모터드라이버 설정
  pinMode(A1B, OUTPUT);
  pinMode(B1A, OUTPUT);
  pinMode(B1B, OUTPUT); 
  pinMode(C1A, OUTPUT);
  pinMode(C1B, OUTPUT);
  pinMode(D1A, OUTPUT);
  pinMode(D1B, OUTPUT);
}

void loop() {
  if (start == 0) {//아래 내용을 1번만 출력하기 위한 조건문
    Serial.println("start");
    BT.println("Please input table number and menu number");
    BT.println("God Father:1 Screw Driver:2 Jackcoke:3");
    BT.println("ex)23");
    BT.println(" ");
    start = 1;
  }

    if (BT.available()) {//블루투스 신호를 받았을 경우
      cmd[0] = BT.read(); 
      cmd[1] = BT.read(); 

      mn = cmd[1];    // 메뉴 번호 저장
      if (mn == '1' || mn == '2' || mn == '3') {
        switch (mn) {
          case '1':
            GodFather();
            break;
          case '2':
            ScrewDriver();
            break;
          case '3':
            JackCoke();
            break;
        }
      }
      start = 0;
    }
}

void GodFather()
{
menu = "God Father";
digitalWrite(A1A, HIGH);
digitalWrite(A1B, LOW);
digitalWrite(D1A, HIGH);
digitalWrite(D1B, LOW);
delay(3000);
digitalWrite(A1A, LOW);
digitalWrite(A1B, LOW);
delay(5000);
digitalWrite(D1A, LOW);
digitalWrite(D1B, LOW);
delay(3000);
}

