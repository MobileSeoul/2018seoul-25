# Seoulite_Android
2018년 서울시 앱 공모전 출품작(Seoulite)



## 협업을 위한 코딩 스타일 가이드

 일관되고 효율적인 의사소통 및 협업을 위해 다음과 같은 코딩 스타일 가이드를 사용합니다.



### JAVA Coding Style

 아래 항목은 안드로이드에서 제공하는 'AOSP(Android OpenSource Project) 를 위한 코딩 규칙'에서 최소한으로 발췌한 내용입니다. 전체 문서를 참조하려면 [여기](https://source.android.com/setup/contribute/code-style)를 클릭하세요.



#### 들여쓰기

 들여쓰기는 기본적으로 **스페이스 4칸**을 사용합니다. 단, 하나의 statement를 여러 줄에 나눠서 작성할 때는 **스페이스 8칸**을 사용합니다. 안드로이드 스튜디오의 기본 설정이므로 바꾸지 말고 사용하면 됩니다( 참고 - 윈도우 운영체제에서의 코드 정리 단축키: ```ctrl``` + ```Alt``` + ```L``` ).

 좋은 예)

```java
SomeClassType c =
        SomeLongExpressions().getClassType();
```

 나쁜 예)

```java
SomeClassType c =
    SomelongExpressions().getClassType();
```



#### 필드 이름 규칙

- Non-public, Non-static 필드 이름은 ```m```으로 시작합니다.
- Static 필드 이름은 ```s```로 시작합니다.
- 다른 필드는 알파벳 소문자로 시작합니다.
- Public Static Final 필드(상수) 는 모두 대문자로 작성하며, 단어 사이에 `_`를 추가합니다.

 예)

```java
public class ExamClass {
    public static final int CONSTANT_NAME = 100;
    public int publicField;
    private static ExamClass sSingleton;
    int mPackagePrivate;
    protected int mProtected;
    private int mPrivate;
}
```



#### 괄호 사용

 괄호는 따로 개행한 뒤 작성하지 않고 이전 코드와 같은 줄에서 열어서 사용합니다. 단, 한 줄의 짧은 코드를 괄호 안에 넣는 경우는 괄호를 생략하고 같은 줄에 코드를 작성할 수 있습니다.

 예)

```java
public class MyClass {
    void testMethod() {
        if (isSomething) {
            setValue();
        } else {
            setSomethingDifferent();
        }
    }
    
    void anotherMethod() {
        if (isSomething) setAnotherValue();
    }
}
```

 나쁜 예)

```java
if (isCondition)
    doNotDoThis();
```



#### 라인 글자 수 제한

 한 줄에는 다음 두 경우의 예외를 제외하고 최대 100글자를 넘지 않도록 조절합니다. 

1. 주석의 경우 예시 명령어 혹은 URL을 작성할 때에는 복사/붙여넣기의 편의를 위해 한 줄에 작성합니다.
2. import문의 경우에는 잘 보이지도 않고 볼 일도 별로 없기 때문에 편의상 한줄에 작성합니다.



#### 약어, 이니셜 등을 포함한 이름

 약어 혹은 이니셜은 한 단어와 똑같이 취급합니다.

 좋은 예)

```java
public interface XmlHttpRequest {
    String url;
    long id;
    
    long getId();
}
```

 나쁜 예)

```java
public interface XMLHTTPRequest {
    String URL;
    long ID;
    
    long getID();
}
```



#### TODO의 작성

 일시적으로 작성한 코드, 혹은 완벽하지 않은 코드나 개선이 필요한 코드, 나중에 더 작업해야 할 일을 표시할 때에는 TODO 주석을 작성합니다. TODO 주석은 일반 주석에서 모두 대문자로 `TODO`를 작성 후 콜론(:)을 사용한 후 작성합니다.

 예)

```java
// TODO: Need to implement a custom listener.
```



### XML Naming Convention

 아래 내용은 *Jeroen Mols*가 작성한 _'A successful XML naming convention'_이라는 글에서 최소한으로 발췌한 내용입니다. 전체 글을 보려면 [여기](https://jeroenmols.com/blog/2016/03/07/resourcenaming/)를 클릭하세요.



#### 기본 원칙

 모든 리소스 이름은 ```<What>_<Where>_<Description>_<Size>```의 기본 원칙을 따릅니다.

1. What

   리소스가 무엇을 나타내는지 명시합니다. (예: MainActivity의 경우 -> ```activity```) 

2. Where

   리소스가 어플리케이션에 논리적으로 어디에 위치하는지 나타냅니다.

   (예: MainActivity의 경우 -> ```main```)

3. Description

   한 스크린 내의 요소들을 구별하는 데 사용됩니다. (예: ```title```)

4. Size (Optional)

   보통 drawable의 사이즈를 나타낼 때 사용됩니다. (예: ```small```)



#### Layouts

 레이아웃은 ```<what>_<Where>.xml```의 형태로 작성합니다.

예) `activity_main.xml`, `fragment_color.xml`



#### Strings

 문자열의 경우 `<Where>_<Description>`의 형태를 따릅니다. 단, 앱 전체에서 그 문자열이 사용되는 경우에는 `all_<Description>`을 사용합니다.

 예) `main_title`, `all_greetings`



#### Drawables

 이 경우 `<Where>_<Description>_<Size>`로 하는 것이 기본이나 `<Size>`의 경우 선택사항이며, 문자열과 같이 앱 전체에서 사용될 경우 `all_<Description>_<Size>`의 형태를 사용합니다.

 예) `all_infoicon_24dp`, `title_placeholder`



#### IDs

 ID는 `<What>_<Where>_<Description>`의 형태를 따릅니다. `<Description>`의 경우 한 화면 내에 비슷한 요소들을 구분하기 위한 것으로 선택사항입니다.

 예) `textiew_articledetail_title`, `imageview_menu_profile`



**주의사항**: 모든 화면 요소(View)는 XML 네이밍 규칙의 충돌을 방지하기 위해 중복되지 않는 고유한 이름을 가져야 합니다. MainFragment와 MainActivity가 있다면 `<Where>` 부분에 똑같이 `main`이 되기 때문에 구별이 힘들어집니다. 



위에 참조된 원문 링크의 페이지에서 Android Resources Convention Cheat Sheet을 제공하고 있습니다. 확인하려면 위의 원문 링크 혹은 [여기](https://jeroenmols.com/img/blog/resourcenaming/resourcenaming_cheatsheet.pdf)를 클릭하세요.
