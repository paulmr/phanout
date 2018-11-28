package gu.com.phanout

import org.scalajs.dom.experimental.URL
import scala.scalajs.js.URIUtils.encodeURIComponent
import org.scalajs.dom._

object Phanout {

  def makeOphanUrl(path: String) =
    s"https://dashboard.ophan.co.uk/info/json?path=${encodeURIComponent(path)}"

  private def log(s: String) = println(s"[PMR] $s")

  def ophanLookup(url: String) = {
    val path = new URL(url).pathname
    val xhr = new XMLHttpRequest()
    log(makeOphanUrl(path))
    // xhr.open("GET", )
  }

  def getGuardianLinks() = {
    val nodes =
      nodeListIterator(
        document.querySelectorAll(
          Seq(
            """a[href^="https://www.theguardian.com"]""",
            """a[href^="https://www.guardian.co.uk"]""",
            """a[href^="http://www.theguardian.com"]""",
            """a[href^="http://www.guardian.co.uk"]"""
          ).mkString(",")
        )
      )
    (nodes.collect {
      case a: html.Anchor => a
    }).toList
  }

  def nodeListIterator(nodes: NodeList) = new Iterator[Node] {
    private var index = 0
    def hasNext = index < nodes.length
    def next() = {
      val res = nodes(index)
      index = index + 1
      res
    }
  }

  def main(args: Array[String]): Unit = {
    val links = getGuardianLinks()
    /* now for each guardian link, we want to lookup the URL in Ophan */
    for(l <- links) {
      ophanLookup(l.href)
    }
  }
}
