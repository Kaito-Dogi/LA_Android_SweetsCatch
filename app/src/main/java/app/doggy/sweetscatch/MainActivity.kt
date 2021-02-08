package app.doggy.sweetscatch

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    companion object {
        //画像を表示する個数を決める変数。
        private const val IMAGE_NUM: Int = 2

    }

    //カロリー（ポイント）を宣言。
    var kcal = 0

    //画像の配列。
    val images: Array<Int> = arrayOf(
            R.drawable.candy,
            R.drawable.cookie,
            R.drawable.donut,
            R.drawable.cake
    )

    //ImageViewに画像が表示されているかを管理する配列。
    val hasImage: Array<Int?> = arrayOfNulls(16)

    //表示する画像の種類を決める変数。
    var imageIndex = 0

    //画像を表示する場所を決める変数。
    var placeIndex = 0

    //ゲーム中かどうかを管理する変数。
    var status = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //imageViewの配列。
        val imageViews: Array<ImageView> = arrayOf(
            sweetsImageView1, sweetsImageView2, sweetsImageView3, sweetsImageView4,
            sweetsImageView5, sweetsImageView6, sweetsImageView7, sweetsImageView8,
            sweetsImageView9, sweetsImageView10, sweetsImageView11, sweetsImageView12,
            sweetsImageView13, sweetsImageView14, sweetsImageView15, sweetsImageView16
        )

        //初期設定。
        for (i in imageViews.indices) {

            //皿の画像を設定する。
            imageViews[i].setImageResource(R.drawable.dish)
            hasImage[i] = -1

            //クリックリスナの設定。
            imageViews[i].setOnClickListener(CatchListener(i, imageViews))
        }

        startButton.setOnClickListener {

            when(status) {
                0 -> {

                    //画像の初期配置。
                    for (imageNum in 0 until IMAGE_NUM) {
                        displayImage(imageViews)

                    }

                    //ボタンの色を変更。
                    startButton.setBackgroundColor(Color.GRAY)

                    //statusを更新。
                    status = 1
                }
            }
        }
    }

    private inner class CatchListener(val index: Int, val imageViews: Array<ImageView>): View.OnClickListener {
        override fun onClick(view: View) {

            if (hasImage[index] != -1) {

                //カロリーを加算。
                when(hasImage[index]) {
                    0 -> kcal += 10
                    1 -> kcal += 20
                    2 -> kcal += 50
                    3 -> kcal += 100
                }

                kcalText.text = "$kcal kcal"

                //皿の画像を設定する。
                imageViews[index].setImageResource(R.drawable.dish)
                hasImage[index] = -1

                //画像を設定する。
                displayImage(imageViews)

            }
        }
    }

    private fun displayImage(imageViews: Array<ImageView>) {

        //表示する画像を決める。
        imageIndex = Random.nextInt(100)
        when(imageIndex) {
            in 0..49 -> imageIndex = 0
            in 50..79 -> imageIndex = 1
            in 80..94 -> imageIndex = 2
            in 95..99 -> imageIndex = 3
        }

        //表示する場所を決める。
        while (true) {
            placeIndex = Random.nextInt(imageViews.size)
            if (hasImage[placeIndex] == -1) break
        }

        //hasImageの値によって処理を変える。
        if(hasImage[placeIndex] ==  -1) {

            //画像を表示する。
            imageViews[placeIndex].setImageResource(images[imageIndex])

            //hasImageを更新する。
            hasImage[placeIndex] = imageIndex

        }
    }
}