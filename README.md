# SkinSwitcher  [![](https://jitpack.io/v/aliletter/skinswitcher.svg)](https://jitpack.io/#aliletter/skinswitcher)
SkinSwitcher is a dynamic open-source framework for skin peeing. After successful skinning, SkinSwitcher can achieve the peeing effect without restarting the application. It also supports the dynamic replacement of most of the properties defined in the layout file. The skin resource comes from the external apk file, not the skin resources inside the app.
## Instruction
The attribute resources that need to be replaced must be referenced (@dimen/XXXXX), and the direct type (XXDP) cannot be changed. The properties of the skin change control must have corresponding methods. If not, developers can do the method.
### Code Sample
```Java
public class BaseActivity extends Activity{
    protected SkinFactory factory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        factory = new SkinFactory() {
            /**
             * Attribute filter
             * @param attrName textColor,background,drawable and so on.
             * @return if ture,that attribute is what we need to replace.
             */
            @Override
            public boolean onAttributeFilter(String attrName) {
                return attrName.contains("background");
            }

            /**
             * The skin switcher, when we set the skin package, the two methods (onAttributeFilter, onSwitcher) will be called.
             * @param view If the current Activity contains multiple controls that need to be changed, this method also corresponds to the number of times the user needs to determine the type of the view.
             * @param attrType This property is used to assist the user to determine the type of resource that the current view needs to replace. (string,mipmap,drawable,dimen,color and so on)
             * @param attrName Property name. This property is filtered through the onAttributeFilter method. The user can set the properties of the View by calling the corresponding method to achieve the purpose of skin change.（textColor,background,drawable and so on）
             * @param attrValue Attribute reference value.(@dimen/xxx,@color/xxxx,@mimap/xxxx and so on)
             * @param attrObj The value of the attribute read in the skin package.
             */
            @Override
            public void onSwitcher(View view, ResourceType attrType, String attrName, String attrValue, Object attrObj) {
                view.setBackgroundColor((Integer) attrObj);
            }
        };
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), factory);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        factory.apply();
    }
}
```

```Java
public class BaseActivity extends Activity implements SkinFactory2.OnSkinFactory {
    protected SkinFactory2 factory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        factory=new SkinFactory2();
        factory.setOnSkinFactory(this);
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), factory);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        factory.apply();
    }

    @Override
    public boolean onAttributeFilter(String attrName) {
        return attrName.contains("background");
    }

    @Override
    public void onSwitcher(View view, ResourceType attrType, String attrName, String attrValue, Object attrObj) {
        view.setBackgroundColor((Integer) attrObj);
    }
}

```