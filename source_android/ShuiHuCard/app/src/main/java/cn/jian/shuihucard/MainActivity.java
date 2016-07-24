package cn.jian.shuihucard;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.wenchao.cardstack.CardStack;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private CardStack container;
    private ImageView main_img;
    private Button main_reset_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container= (CardStack) findViewById(R.id.container);
        main_img= (ImageView) findViewById(R.id.main_img);
        main_reset_bt= (Button) findViewById(R.id.main_reset_bt);
        final MyDataAdpter myDataAdpter=new MyDataAdpter(this,0);
        for (int i = 0; i <108 ; i++) {
            myDataAdpter.add("cards/"+(i+1));
        }
        container.setContentResource(R.layout.item_card);
        container.setStackMargin(20);
        container.setAdapter(myDataAdpter);

        container.setListener(new CardStack.CardEventListener() {
            @Override
            public boolean swipeEnd(int i, float distance) {
                return (distance>300)? true : false;
            }

            @Override
            public boolean swipeStart(int i, float v) {
                return false;
            }

            @Override
            public boolean swipeContinue(int i, float v, float v1) {
                return false;
            }

            @Override
            public void discarded(int i, int i1) {

            }

            @Override
            public void topCardTapped() {
                main_img.setVisibility(View.VISIBLE);
                main_img.setImageBitmap(getImageFromAssetsFile(myDataAdpter.getItem(container.getCurrIndex())+"-.jpg"));
            }
        });

        main_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_img.setVisibility(View.GONE);
            }
        });

        main_reset_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.reset(true);
            }
        });
    }


    public class MyDataAdpter extends ArrayAdapter<String>{


        public MyDataAdpter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ImageView cardImg= (ImageView) convertView.findViewById(R.id.card_img);
            cardImg.setImageBitmap(getImageFromAssetsFile(getItem(position)+"+.jpg"));
            return convertView;
        }
    }


    /**
     * 从Assets中读取图片 getImageFromAssetsFile("Cat_Blink/cat_blink0000.png");
    */
    private Bitmap getImageFromAssetsFile(String fileName)
    {
        Bitmap image = null;
        AssetManager am = getResources().getAssets();
        try
        {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return image;

    }
}
