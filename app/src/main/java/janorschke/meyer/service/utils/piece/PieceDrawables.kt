package janorschke.meyer.service.utils.piece

import android.content.Context
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import androidx.core.content.ContextCompat.getDrawable
import janorschke.meyer.R
import janorschke.meyer.enums.PieceColor
import janorschke.meyer.service.model.game.piece.Piece

object PieceDrawables {
    /**
     * @return the drawable for possible move on a empty field
     *
     * @see getGrayCircle
     */
    fun getPossibleMove(context: Context): Drawable = getGrayCircle(context)

    /**
     * @param context of the application
     * @param piece information
     * @return the drawable for possible move on a field with an opponent piece
     */
    fun getAttackingPossibleMove(context: Context, piece: Piece): Drawable {
        val layers = mutableListOf<Drawable>()

        getPossibleMove(context).also { drawable ->
            drawable.colorFilter = ColorMatrixColorFilter(getRgbColor(255, 71, 76))
        }.let { drawable -> layers.add(drawable) }

        // Add figure at the top
        layers.add(getPiece(context, piece))

        return LayerDrawable(layers.toTypedArray())
    }

    /**
     * @param context of the application
     * @param piece information
     * @return the drawable for the piece which is currently selected
     */
    fun getSelectedPiece(context: Context, piece: Piece): Drawable {
        val layers = mutableListOf<Drawable>()

        getGrayCircle(context).also { drawable ->
            drawable.colorFilter = ColorMatrixColorFilter(getRgbColor(64, 224, 208, 180))
        }.let { drawable -> layers.add(drawable) }

        // Add figure at the top
        layers.add(getPiece(context, piece))

        return LayerDrawable(layers.toTypedArray())
    }

    /**
     * @param context of the application
     * @param piece information
     * @return the drawable for the piece which is marked as last moved
     */
    fun getLastMovingPiece(context: Context, piece: Piece): Drawable {
        val layers = mutableListOf<Drawable>()

        getGrayCircle(context).also { drawable ->
            drawable.colorFilter = ColorMatrixColorFilter(getRgbColor(184, 132, 40, 180))
        }.let { drawable -> layers.add(drawable) }

        // Add figure at the top
        layers.add(getPiece(context, piece))

        return LayerDrawable(layers.toTypedArray())
    }

    /**
     * @param context context of the application
     * @param piece information
     * @return the drawable of an piece
     */
    fun getPiece(context: Context, piece: Piece): Drawable {
        return getDrawable(context, piece.pieceInfo.imageId)!!
                .mutate()
                .also { drawable ->
                    if (piece.color == PieceColor.BLACK) {
                        floatArrayOf(
                                -1.0f, 0f, 0f, 0f, 255f,  // red
                                0f, -1.0f, 0f, 0f, 255f,  // green
                                0f, 0f, -1.0f, 0f, 255f,  // blue
                                0f, 0f, 0f, 1.0f, 0f // alpha
                        ).let { colorMatrix -> drawable.colorFilter = ColorMatrixColorFilter(colorMatrix) }
                    }
                }
    }

    private fun getRgbColor(r: Int, g: Int, b: Int, a: Int = 255): FloatArray {
        return floatArrayOf(
                0f, 0f, 0f, 0f, r.toFloat(),  // red
                0f, 0f, 0f, 0f, g.toFloat(),  // green
                0f, 0f, 0f, 0f, b.toFloat(),  // blue
                0f, 0f, 0f, 0f, a.toFloat()   // alpha
        )
    }

    /**
     * @param context of the application
     * @return a gray circle
     */
    private fun getGrayCircle(context: Context) = getDrawable(context, R.drawable.gray_circle)!!.mutate()
}