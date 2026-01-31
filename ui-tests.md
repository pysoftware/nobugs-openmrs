## 1. Patient Search / Home

**Позитивные**

- №1. Home → ввести имя или ID в поиск → Enter → клик на пациента  
  → Открывается Patient summary

**Негативные**

- Поиск по несуществующему имени  
  → «Sorry, no patient charts were found», пустой список

## 2. Регистрация нового пациента

**Позитивные**

- №2. Home → Add Patient → имя, фамилия, пол, дата рождения → Register patient  
  → Новый пациент создан, сразу открывается Patient summary

- №3. Регистрация с адресом → Добавить адрес (страна, город, улица) → Register patient  
  → Адрес отображается в Patient Chart → Show more → Address

- №4. Регистрация с атрибутом (email/telephone) → Добавить email или телефон → Register patient  
  → Атрибут виден в Show more → Contact Details

- №5. Открыть Patient summary → посмотреть Header и  Show more
  → Имя, возраст, пол, ID, адрес (если есть) корректно

**Негативные**

- Пропустить обязательное поле (имя или пол) → Register patient   
  → Cообщение «The following fields have errors: First Name» 

- Указать дату рождения в будущем → Register patient   
  → Cообщение «The following fields have errors: birthdate»

## 3. Actions в Patient summary

- №6. Patient summary → Actions → Add to list → кликнуть чек-бокс списка → Save  

  → В Show more → Patient Lists отображается название списка

- №7. Patient summary → Actions → Mark patient deceased → заполнить дату и причину смерти → Save and close  
  → В Header рядом с полом "Deceased"
  → Из Actions исчезло Add visit, добавилось Mark patient alive

- №8. Patient summary → Actions → Edit patient details → новый адрес + дата рождения → Update patient
  → Отображается Новый адрес и дата рождения

**Негативные**

- Указать дату рождения в будущем при редактировании → Update patient 
  → Cообщение «The following fields have errors: birthdate»


## 4. Visits и Visit Types

**Позитивные**

- №9. Patient summary → Actions → Add visit → Start Visit → выбрать Visit location, Visit Type → Start visit
  → Появляется «Active Visit» рядом с именем, при нажатии, отображается дата начала 

- №10. Patient summary → Actions → End active Visit → End Visit 
  → Визит завершён, «Active Visit» рядом с именем исчезает

- №11. Patient summary → Visits tab  
  → Список всех визитов с датами и типами

**Негативные**

- Start Visit без выбора типа 
  → Ошибка «Missing visit type Please select a visit type»

- При активном визите нажать Start Visit  
  → Ошибка «Error starting visit This visit overlaps with another visit of the same patient.»

## 5. Vitals and biometrics

**Позитивные**

- №12. Active Visit → Record vitals → заполнить → Save and close   
  → В Patient summary отображаются введенные данные

- №13. Patient summary → Vitals widget → Add → заполнить → Save and close
  → Новые vitals в списке

**Негативные**

- Нажать Record vitals  Patient summary → Vitals widget → Add
  → Ошибка «You can't add data to the patient chart without an active visit. Would you like to start a new visit?», форма недоступна

-  Patient summary → Vitals widget → Add Patient summary → Vitals widget → Add
   → Ошибка «You can't add data to the patient chart without an active visit. Would you like to start a new visit?», форма недоступна