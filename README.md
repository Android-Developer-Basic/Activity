# Activity #2 Homework

Создайте в модуле **sender** класс **SenderActivity**. Добавьте в него три кнопки: **“To Google Maps”**, **“Send Email”** и **“Open Receiver”**. Добавьте пустые обработчики нажатий на эти кнопки.

1. По клику на кнопку **“To Google Maps”**, используя интент с указанием package `com.google.android.apps.maps`, вызовите `Activity` приложения Google Maps. После того как Google Maps поймает ваш Intent, в нем должны отобразиться ближайшие к текущей геолокации места по тэгу “*Рестораны”*

    <img src="art/Untitled.png" width="520">

2. По клику на кнопку **“Send Email”** отправьте неявный `Intent` в метод `startActivity()` Этот `Intent` должны уметь обработать любые почтовые клиенты(если они реализовали `intent-filter` согласно контракту).
   В качестве адресата используйте ящик *android@otus.ru*, тему и содержание письма придумайте сами.

    <img src="art/Untitled%201.png" width="520">

3. По клику на кнопку **“Open Receiver”** отправьте неявный `Intent` со следующими параметрами:

   - `action = Action.SEND`
   - `type = “text/plain”`
   - `category = Category.DEFAULT`

   В качестве extras отправьте три объекта String. В качестве значений extras используйте любой набор данных из файла *payload.txt*, который лежит в корне проекта **sender**.

   В модуле **receiver** зарегистрируйте `intent-filter`, таким образом, чтобы он поймал отправленный выше `Intent` и открыл **ReceiverActivity**. Полученные из `Intent` extras отобразите в соответсвующих полях:

    - title → `titleTextView`
    - year → `yearTextView`
    - description → `descriptionTextView`
    - В зависимости от названия фильма отобразите картинку которая лежит в ресурсах(*res/drawable*) в `posterImageView`

> 💡 Чтобы достать ресурс, используйте метод [Context.getDrawable()](https://developer.android.com/reference/android/content/Context#getDrawable(int)), а чтобы поменять картинку в ImageView используйте метод [setImageDrawable()](https://developer.android.com/reference/android/widget/ImageView#setImageDrawable(android.graphics.drawable.Drawable))

<img src="art/Untitled%202.png" width="720">
