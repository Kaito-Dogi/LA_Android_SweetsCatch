package app.doggy.sweetscatch

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    companion object {
        //最初に表示する画像の個数。
        private const val IMAGE_NUM = 2

        //制限時間。
        private const val TIME = 15000

    }

    //カロリー（ポイント）を宣言。
    var kcal = 0

    //残り時間の表示の仕方を宣言。
    private val dataFormat: SimpleDateFormat = SimpleDateFormat("ss.SS", Locale.US)

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

        //残り時間を表示する。
        timeText.text = dataFormat.format(TIME.toLong())

        //タイマーを設定する。
        val timer: CountDownTimer = object : CountDownTimer(TIME.toLong(), 10) {

            //タイマーが終了した時に呼ばれる。
            override fun onFinish() {

                //画像を初期化。
                for (i in imageViews.indices) {

                    //皿の画像を設定する。
                    imageViews[i].setImageResource(R.drawable.dish)
                    hasImage[i] = -1

                }

                //スタートボタンを表示する。
                startButton.setBackgroundColor(Color.parseColor("#FF9800"))

                //残り時間を表示する。
                timeText.text = dataFormat.format(TIME.toLong())

                //statusを更新。
                status = 0

            }

            //カウントダウンされる毎に呼び出される。
            override fun onTick(millisUntilFinished: Long) {

                //残り時間を表示する。
                timeText.text = dataFormat.format(millisUntilFinished)

            }
        }


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
                    for (i in 0 until IMAGE_NUM) {
                        displayImage(imageViews)
                    }

                    //ボタンの色を変更。
                    startButton.setBackgroundColor(Color.GRAY)

                    //タイマーをスタート。
                    timer.start()

                    //カロリーをリセット。
                    kcal = 0

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
                    1 -> kcal += 35
                    2 -> kcal += 100
                    3 -> kcal += 250
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
            in 0..69 -> imageIndex = 0
            in 70..89 -> imageIndex = 1
            in 90..96 -> imageIndex = 2
            in 97..99 -> imageIndex = 3
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