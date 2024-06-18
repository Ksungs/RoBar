#include <Servo.h>

Servo servoLeft; 
Servo servoRight; //모터
Servo servoR; //서보
int t = 0; // 적외선에 따라 테이블 번호가 저장되는 변수
int ir = 1;
long time; // 일정 시간동안 적외선 감지 횟수를 측정하기 위해 현재 시간이 저장되는 변수

void setup()
{
    delay(1000);
    pinMode(9, INPUT);
    pinMode(8, OUTPUT); // 적외선 센서 및 LED
    servoLeft.attach(13);
    servoRight.attach(12); // 모터
    servoR.attach(11);
    Serial.begin(9600);
}

void loop()
{
    tone(8, 38000, 10); // 대기상태를 알리기 위한 적외선 방출
    delay(10);
    servoR.write(180); // 서브모터 초기화
    ir = irDetect(9); // 적외선값 측정
    Serial.print("ir= ");
    Serial.println(ir);
    if (!ir) // 적외선이 감지되면 3.2초간 적외선 감지 횟수 측정
    {
        delay(200);
        time = millis(); // 현재시간 저장
        while (time > millis() - 3200) // 현재시간을 time에 저장된 시간과 비교하여 3.2초동안 while문 실행
        {
            ir = irDetect(9);
            if (!ir)
            {
                Serial.println("감지됨감지됨감지됨감지됨감지됨감지됨감지됨");
                t++; // 적외선을 받은 횟수에 따라 t값 변경
                delay(100);
            }
            else
            {
                Serial.println("감지안됨");
            }
        } // while문 종료
        Serial.print("t= ");
        Serial.println(t);
        delay(2000);
        if (t > 0)
        {
            forward(500 + 550 * (t - 1));
            servoRf(); // 테이블 번호에 따라 전진 후 서브모터를 사용하여 음료 전달
            delay(1000);
            backward(500 + 550 * (t - 1) + 300); // 복귀할 때 후진값에 +300을 주어 로봇이 벽에 정렬되도록 함
        }
        t = 0; // t값 초기화
        delay(100);
    }
    ir = 1; // 적외선값 초기화
} // loop 함수 종료

// 적외선 감지 함수
int irDetect(int irReceiverPin)
{
    int ir = digitalRead(irReceiverPin);
    delay(1);
    return ir;
} // 적외선 감지

// 전진
void forward(int time)
{
    servoLeft.writeMicroseconds(1300);
    servoRight.writeMicroseconds(1700);
    delay(time);
    servoLeft.writeMicroseconds(1500);
    servoRight.writeMicroseconds(1500);
} // 전진

// 후진
void backward(int time)
{
    servoLeft.writeMicroseconds(1700);
    servoRight.writeMicroseconds(1300);
    delay(time);
    servoLeft.writeMicroseconds(1500);
    servoRight.writeMicroseconds(1500);
} // 후진

// 오른쪽 서보 회전
void servoRf()
{
    int i;
    for (i = 180; i >= 90; i -= 3)
    {
        servoR.write(i);
        delay(50);
    }
    servoR.write(180);
} // 오른쪽 서보 회전
