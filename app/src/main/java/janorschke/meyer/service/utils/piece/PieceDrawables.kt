package janorschke.meyer.service.utils.piece

import android.content.Context
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import androidx.core.content.ContextCompat
import janorschke.meyer.R
import janorschke.meyer.service.model.piece.Piece
import janorschke.meyer.enums.PieceColor

object PieceDrawables {
    /**
     * @param context of the application
     * @return the drawable for possible move on a empty field
     */
    fun getPossibleMove(context: Context): Drawable = ContextCompat.getDrawable(context, R.drawable.chess_possiblemove)!!.mutate()

    /**
     * @param context of the application
     * @param piece information
     * @return the drawable for possible move on a field with an opponent piece
     */
    fun getAttackingPossibleMove(context: Context, piece: Piece): Drawable {
        val layers = mutableListOf<Drawable>()

        getPossibleMove(context)
                .also { drawable ->
                    floatArrayOf(
                            1f, 0.16f, 0.14f, 1f, 1f,  // red
                            0f, 0f, 0f, 0f, 0f,  // green
                            0f, 0f, 0f, 0f, 0f,  // blue
                            1f, 1f, 1f, 1f, 1f // alpha
                    ).apply { drawable.colorFilter = ColorMatrixColorFilter(this) }
                }.apply { layers.add(this) }

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
        return ContextCompat.getDrawable(context, piece.pieceInfo.imageId)!!.mutate().also { drawable ->
            if (piece.color == PieceColor.BLACK) {
                floatArrayOf(
                        -1.0f, 0f, 0f, 0f, 255f,  // red
                        0f, -1.0f, 0f, 0f, 255f,  // green
                        0f, 0f, -1.0f, 0f, 255f,  // blue
                        0f, 0f, 0f, 1.0f, 0f // alpha
                ).apply { drawable.colorFilter = ColorMatrixColorFilter(this) }
            }
        }
    }
}