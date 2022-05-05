package services

import java.awt.Color

import javax.inject.Inject
import play.api.{Configuration, Logger}

import scala.concurrent.ExecutionContext
import javax.imageio.ImageIO
import java.io.File
import java.awt.image.BufferedImage


class ImageService @Inject()(
                              config: Configuration
                            )(implicit ec: ExecutionContext) {

  private val log = Logger(this.getClass)

  val dirPath = config.get[String]("data.dir")

  def convert(file: String): Boolean = {
    try {
      val imagefile = new File(s"$dirPath/$file")
      val inImg = ImageIO.read(imagefile)
      val outImg = new BufferedImage(inImg.getWidth, inImg.getHeight, BufferedImage.TYPE_INT_RGB)
      val graphic = outImg.createGraphics
      graphic.drawImage(inImg, 0, 0, Color.WHITE, null)
      for (i <- 0 until outImg.getHeight; j <- 0 until outImg.getWidth) {
        val c = new Color(outImg.getRGB(j, i))

        val red = (c.getRed() * 0.299).toInt
        val green = (c.getGreen() * 0.587).toInt
        val blue = (c.getBlue() * 0.114).toInt
        val newColor = new Color(
          red + green + blue,
          red + green + blue,
          red + green + blue)
        outImg.setRGB(j, i, newColor.getRGB())
      }
      ImageIO.write(outImg, "jpg",
        new File(s"$dirPath/converted-$file"))
      true
    } catch {
      case ex: Exception =>
        log.error(s"ImageService.convert error ${ex.getMessage}")
        false
    }
  }

  def listFiles: List[String] = {
    val d = new File(dirPath)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).map(_.getName).filterNot(_.contains("converted-")).toList
    } else {
      List[String]()
    }
  }

}
