import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import collection.mutable.HashMap
import scala.collection.mutable.Stack
import collection.mutable.HashSet
import scala.collection.immutable.TreeMap

object Solution {


  def main(args: Array[String]) = {


    class TreeNode(var _value: Int) {
      var value: Int = _value
      var left: TreeNode = null
      var right: TreeNode = null
    }

    class Node(var _value: Int, var _number: Int) {
      var value: Int = _value
      var number: Int = _number
      var left: Node = null
      var right: Node = null}

      def searchRange(nums: Array[Int], target: Int): Array[Int] = {
        var result: Array[Int] = Array()
        var index = (nums.length / 2).toInt
        if (nums(index) > target) {

        }

        Array(1)

      }


      def findWords(words: Array[String]): Array[String] = {
        val row1 = "qwertyuiop"
        val row2 = "asdfghjkl"
        val row3 = "zxcvbnm"

        var map1 = Map[Char, Int]()
        row1.foreach(x => map1 += (x.toLower -> x.toLower.toInt))

        var map2 = Map[Char, Int]()
        row2.foreach(x => map2 += (x.toLower -> x.toLower.toInt))

        var map3 = Map[Char, Int]()
        row3.foreach(x => map3 += (x.toLower -> x.toLower.toInt))

        var result = Array[String]()
        for (word <- words) {
          var result1 = true
          var result2 = true
          var result3 = true
          for (chars <- word) {
            if (!map1.contains(chars.toLower)) {
              result1 = false
            }
            if (!map2.contains(chars.toLower)) {
              result2 = false
            }
            if (!map3.contains(chars.toLower)) {
              result3 = false
            }
          }

          if (result1) {
            result = result :+ word
          }
          if (result2) {
            result = result :+ word
          }
          if (result3) {
            result = result :+ word
          }

        }
        result
      }


      def arrayPairSum(nums: Array[Int]): Int = {
        if (nums.length < 2) {
          nums.min
        } else if (nums.length == 3) {
          nums.min + nums.max
        }
        else {
          scala.util.Sorting.quickSort(nums)
          var index = nums.length - 2
          var result = 0
          while (index >= 0) {
            println(s"Adding ${nums(index)}")
            result = result + nums(index)
            index = index - 2
          }
          result
        }
      }

      def mergeTrees(t1: TreeNode, t2: TreeNode): TreeNode = {
        var t3: TreeNode = null

        if (t1 == null && t2 == null) {
          t3 = null
        } else {
          if (t1 != null && t2 != null) {
            t3 = new TreeNode(t1.value + t2.value)
            t3.left = mergeTrees(t1.left, t2.left)
            t3.right = mergeTrees(t1.right, t2.right)
          } else if (t1 == null && t2 != null) {
            t3 = new TreeNode(t2.value)
            t3.left = mergeTrees(null, t2.left)
            t3.right = mergeTrees(null, t2.right)
          } else if (t1 != null && t2 == null) {
            t3 = new TreeNode(t1.value)
            t3.left = mergeTrees(t1.left, null)
            t3.right = mergeTrees(t1.right, null)
          }

        }
        t3
      }


      def reverseWords(s: String): String = {
        s.split(" ", -1).map(_.reverse).mkString(" ")
      }

      def distributeCandies(candies: Array[Int]): Int = {

        var result = Map[Int, Int]()
        candies.foreach(x => result += (x -> 1))
        1

      }

      def islandPerimeter(grid: Array[Array[Int]]): Int = {

        val y = grid.length //how tall it is
        val x = grid(0).length //how wdith it is
        var checklist = new Array[Array[Int]](grid.length)
        grid.copyToArray(checklist)
        var max = 0
        checklist = checklist.map(_.map(x => 0))

        for (j <- 0 to grid.length - 1) {
          for (i <- 0 to grid(j).length - 1)
            if (checklist(j)(i) == 0 && grid(j)(i) == 1) {
              val thisIsalnd = getSurrdendering(j, i)
              if (thisIsalnd > max) {
                max = thisIsalnd
              }
            }
        }
        def getSurrdendering(height: Int, width: Int): Int = {

          checklist(height)(width) = 1
          //println(0 < left)
          //println(s"lef is ${left} and right is ${right} and up is ${up} and down is ${down}")
          //println(s"leftout is ${leftOutOfBound} and right is ${rightOutOfBound} and up is ${upOutOfBound} and down is ${down}")

          var thisBlock = 4
          var toMinus = ArrayBuffer[(Int, Int)]()
          var leftOutOfBound = false
          var rightOutOfBound = false
          var upOutOfBound = false
          var downOutOfBound = false

          val left = width - 1
          val right = width + 1
          val up = height - 1
          val down = height + 1

          if (left < 0) {
            leftOutOfBound = true
          }
          if (right >= x) {
            rightOutOfBound = true
          }
          if (up < 0) {
            upOutOfBound = true
          }
          if (down >= y) {
            downOutOfBound = true
          }
          //println(s"block location is ${blockLocation} and checklist is ${checklist(blockLocation)}")
          if (!leftOutOfBound && checklist(height)(left) == 0 && grid(height)(left) == 1) {
            checklist(height)(left) = 1
            thisBlock = thisBlock + getSurrdendering(height, left)
          }
          if (!rightOutOfBound && checklist(height)(right) == 0 && grid(height)(right) == 1) {
            checklist(height)(right) = 1
            thisBlock = thisBlock + getSurrdendering(height, right)
          }
          if (!upOutOfBound && checklist(up)(width) == 0 && grid(up)(width) == 1) {
            checklist(up)(width) = 1
            thisBlock = thisBlock + getSurrdendering(up, width)
          }
          if (!downOutOfBound && checklist(down)(width) == 0 && grid(down)(width) == 1) {
            checklist(down)(width) = 1
            thisBlock = thisBlock + getSurrdendering(down, width)
          }

          if (!leftOutOfBound && grid(height)(left) == 1) {
            //println("minus left")
            toMinus += (height -> left)
          }
          if (!rightOutOfBound && grid(height)(right) == 1) {
            // println("minus right")
            toMinus += (height -> right)
          }
          if (!upOutOfBound && grid(up)(width) == 1) {
            // println("minus up")
            toMinus += (up -> width)
          }
          if (!downOutOfBound && grid(down)(width) == 1) {
            // println("minus down")
            toMinus += (down -> width)
          }
          val toMinusResult = toMinus.toList.distinct.length
          //toMinus.toList.distinct.foreach(println)
          // println(s"block location is ${height}, ${width} and currently parameter is ${thisBlock-toMinusResult}")
          thisBlock - toMinusResult
        }
        max
      }

      //  val input = Array(Array(0,1,0,0),Array(1,1,1,0),Array(0,1,0,0),Array(1,1,0,0))
      //val input = Array(Array(1),Array(1))
      //val input = Array(Array(1,1),Array(1,0))
      // println(islandPerimeter(input))
      /*
    val t1 = new TreeNode(2)
    t1.left = new TreeNode(0)
    t1.left.right = new TreeNode(1)
    t1.right = new TreeNode(3)
    t1.left.left = new TreeNode(-4)

    val t2 = new TreeNode(5)
    t2.right = new TreeNode(13)
    t2.left = new TreeNode(2)

    val t3 = new TreeNode(1)
    t3.left = new TreeNode(0)
    t3.left.left = new TreeNode(-2)
    t3.right = new TreeNode(4)
    t3.right.left = new TreeNode(3)

    val t4 = new TreeNode(4)
    t4.left = new TreeNode(2)
    t4.left.left = new TreeNode(-3)
    t4.left.left.right = new TreeNode(-1)
    t4.left.left.right.right = new TreeNode(0)
 */
      def averageOfLevels(root: TreeNode): Array[Double] = {

        var resultLevel = new ArrayBuffer[Double]()
        resultLevel += root.value
        val inputLevel = new scala.collection.mutable.Stack[TreeNode]
        if (root.left != null) {
          inputLevel.push(root.left)
        }
        if (root.right != null) {
          inputLevel.push(root.right)
        }

        while (inputLevel.length != 0) {
          loopLevel()
        }

        def loopLevel() = {
          var outputArray = ArrayBuffer[TreeNode]()
          val currentLevelLength = inputLevel.length
          var levelResult = 0.00000
          for (i <- inputLevel) {
            levelResult += i.value
            if (i.left != null) {
              outputArray += i.left
            }
            if (i.right != null) {
              outputArray += i.right
            }
            inputLevel.pop()
            levelResult = levelResult / currentLevelLength
            resultLevel += levelResult
          }
          outputArray.toArray.map(x => inputLevel.push(x))
        }
        resultLevel.toArray
      }

      def singleNumber2(nums: Array[Int]): Int = {

        var result = -1
        val map = new HashMap[Int, Int]()

        val verified = Array.fill[Int](nums.length)(0)
        for (i <- nums) {
          if (map.contains(i)) {
            map(i) = map(i) + 1
          } else {
            map += (i -> 1)
          }
        }

        for (i <- map) {
          if (i._2 == 1) {
            result = i._1
          }
        }

        result

      }

      def singleNumber(nums: Array[Int]): Int = {
        var result = 0
        if (nums.length == 1) {
          result = nums(0)
        } else {
          for (i <- 0 to nums.length - 1) {
            result = result ^ nums(i)
          }
        }
        result
      }

      def findMaxConsecutiveOnes(nums: Array[Int]): Int = {

        var max = 0
        var counter = 0
        for (i <- nums) {
          if (i == 1) {
            counter += 1
          }
          if (counter > max) {
            max = counter
          }
          if (i == 0) {
            counter = 0
          }
        }
        max
      }

      def printTree(newRoot: TreeNode): Int = {
        if (newRoot != null) {
          println(s"Current level is ${newRoot.value}")
        }

        if (newRoot.left != null) {
          println(s"left node  is ${newRoot.left.value}")
        }

        if (newRoot.right != null) {
          println(s"right node is ${newRoot.right.value} ")
        }

        if (newRoot.left != null) {
          printTree(newRoot.left)
        } else {
          println("I have no left")
        }


        if (newRoot.right != null) {
          printTree(newRoot.right)
        } else {
          println("I have no rigth")
        }
        1
      }

      def printNode(newRoot: Node): Unit = {
        if (newRoot != null) {
          println(s"Current value is ${newRoot.value} and number is ${newRoot.number}")
        }

        if (newRoot.left != null) {
          println(s"left node value  is ${newRoot.left.value} and number is ${newRoot.left.number}")
        }

        if (newRoot.right != null) {
          println(s"right node value  is ${newRoot.right.value} and number is ${newRoot.right.number}")
        }

        if (newRoot.left != null) {
          printNode(newRoot.left)
        } else {
          println("I have no left")
        }
        if (newRoot.right != null) {
          printNode(newRoot.right)
        } else {
          println("I have no rigth")
        }
      }

      def maxDepth(root: TreeNode): Int = {

        def findPath(newRoot: TreeNode, current: Int): Int = {
          var currentMax = 0
          var leftResult = 0
          var rightResult = 0
          if (newRoot.left != null) {
            leftResult = findPath(newRoot.left, current + 1)
          }
          if (newRoot.right != null) {
            rightResult = findPath(newRoot.right, current + 1)
          }

          //currentMax = Math.max(current,Math.max(Math.max(max, leftResult), rightResult))
          currentMax = Math.max(current, Math.max(leftResult, rightResult))
          currentMax
        }

        var max = 0

        if (root != null) {
          max = findPath(root, 0) + 1
        }
        max

      }

      def detectCapitalUse(word: String): Boolean = {

        val firstCap = word.charAt(0).isUpper
        var result = true

        if (!firstCap) {
          for (i <- word) {
            if (i.isUpper) {
              result = false
            }
          }
        } else {
          var allCap = true
          var capCounter = 0
          for (i <- 1 to word.length - 1) {
            if (word(i).isUpper) {
              capCounter += 1
            } else {
              allCap = false
            }
          }

          if (!allCap && capCounter != 0) {
            result = false
          }
        }
        result

      }

      def invertTree(root: TreeNode): TreeNode = {

        if (root != null) {
          //println(s"I am on this floor ${root.value}")
          val temp = root.left
          root.left = root.right
          root.right = temp
          invertTree(root.left)
          invertTree(root.right)
        }

        root
      }

      /*
        val t1 = new TreeNode(1)
        t1.right = new TreeNode(2)
        t1.left = new TreeNode(3)
        t1.left.left = new TreeNode(5)

        val t2 = new TreeNode(2)
        t2.left = new TreeNode(1)
        t2.right = new TreeNode(3)
        t2.right.right = new TreeNode(7)
        t2.right.left = new TreeNode(4)

        printTree(mergeTrees(t1,t2))
        println()*/
      def judgeCircle(moves: String): Boolean = {
        var x = 0
        var y = 0

        for (i <- moves) {

          if (i == 'U') {
            y += 1
          } else if (i == 'D') {
            y -= 1
          } else if (i == 'L') {
            x += 1
          } else {
            x -= 1
          }
        }
        (x == 0 && y == 0)

      }

      def nextGreaterElement(nums1: Array[Int], nums2: Array[Int]): Array[Int] = {

        var map1 = Map[Int, Int]()
        val pending = Stack[Int]()
        val ResultBuff = ArrayBuffer[Int]()

        for (i <- nums2) {
          if (pending.length != 0) {
            while (pending.length != 0 && pending.top < i) {
              map1 += (pending.pop() -> i)
            }
          }
          pending.push(i)
        }
        for (j <- nums1) {
          ResultBuff += map1.getOrElse(j, -1)
        }

        ResultBuff.toArray
      }

      //nextGreaterElement(Array(4,1,2),Array(1,3,4,2)).foreach(println)

      def getSum(a: Int, b: Int): Int = {

        val aBinary = String.format("%16s", Integer.toBinaryString(a)).replace(' ', '0')
        val bBinary = String.format("%16s", Integer.toBinaryString(a)).replace(' ', '0')
        var plusOne = 0
        val resultBinary = ArrayBuffer[Int]()
        val length = Math.min(aBinary.length, bBinary.length)
        val leftOver = aBinary.length > bBinary.length

        println(s"a array is ${aBinary.foreach(print)}")
        println(s"b array is ${bBinary.foreach(print)}")
        println(s"lenght is ${length}")
        for (i <- length - 1 to 0 by -1) {
          println(s"i = ${i}")
          println(s"a = ${aBinary(i)} and b = ${bBinary(i)}")
          if (aBinary(i) == 1 && bBinary(i) == 1) {
            if (plusOne == 1) {
              resultBinary += 1
              plusOne = 1
            } else {
              plusOne = 1
              resultBinary += 0
            }
          } else if (aBinary(i) != bBinary(i)) {


            if (plusOne == 1) {
              resultBinary += 0
              plusOne = 0
            } else {
              resultBinary += 1
            }
          } else {
            resultBinary += 0
          }
        }

        if (leftOver) {
          for (i <- bBinary.length - 1 to aBinary.length - 1) {
            resultBinary += aBinary(i)

          }
        } else {
          for (i <- aBinary.length - 1 to bBinary.length - 1) {
            resultBinary += bBinary(i)
          }
        }
        Integer.parseInt(resultBinary.toArray.mkString(""), 2)
      }

      def addDigits2(num: Int): Int = {

        var numString = num.toString

        var temp = 0
        var test = true
        while (numString.length != 1) {

          for (i <- numString) {
            temp += i.asDigit
          }
          //println(temp)
          numString = temp.toString
          temp = 0
        }

        numString.toInt
      }

      def addDigits(num: Int): Int = {

        var result = 0
        val leftOver = num % 9
        if (num != 0) {
          if (leftOver == 0) {
            result = 9
          } else {
            result = leftOver
          }
        }

        result
      }

      def findTarget(root: TreeNode, k: Int): Boolean = {

        val hashset1 = new HashSet[Int]()
        var result = false
        loopTree(root)
        def loopTree(newRoot: TreeNode): Unit = {
          // println(s"looking for ${k-newRoot.value}")
          if (!hashset1.contains(k - newRoot.value)) {
            hashset1 += newRoot.value
            if (newRoot.left != null) {
              loopTree(newRoot.left)
            }
            if (newRoot.right != null) {
              loopTree(newRoot.right)
            }
          } else {
            result = true
          }
        }
        result
      }

      def findTheDifference(s: String, t: String): Char = {

        var result = 0

        for (i <- s) {
          result = result ^ i.toInt
        }

        for (i <- t) {
          result = result ^ i.toInt
        }

        result.toChar
      }

      def moveZeroes(nums: Array[Int]): Unit = {

        var changeIndex = 0
        for (i <- 0 to nums.length - 1) {
          if (nums(i) != 0) {
            nums(changeIndex) = nums(i)
            if (changeIndex != i) {
              nums(i) = 0
            }
            changeIndex += 1
          }
        }
        nums.foreach(println)
      }

      def tree2str(t: TreeNode): String = {

        val result = new StringBuilder()
        loopTreeNode(t)
        def loopTreeNode(newRoot: TreeNode): Unit = {
          if (newRoot != null) {
            var needEmpty = false
            result.append(newRoot.value)
            if (newRoot.left != null) {
              result.append("(")
              loopTreeNode(newRoot.left)
              result.append(")")
            } else {
              needEmpty = true
            }
            if (newRoot.right != null) {
              if (needEmpty == true) {
                result.append("()")
              }
              result.append("(")
              loopTreeNode(newRoot.right)
              result.append(")")
            }
          }
        }
        result.toString()
      }

      def convertBST(root: TreeNode): TreeNode = {
        var sum = 0
        find(root)
        def find(newRoot: TreeNode): Unit = {
          if (newRoot != null) {
            if (newRoot.right != null) {
              find(newRoot.right)
            }
            sum += newRoot.value
            newRoot.value = sum
            if (newRoot.left != null) {
              find(newRoot.left)
            }
          }
        }
        root
      }

      def minMoves(nums: Array[Int]): Int = {

        val temp = nums
        var result = false
        var counter = 0
        while (!result) {
          var sameNumber = true
          counter += 1
          val max = temp.max
          var haveMax = false
          for (i <- 0 to temp.length - 1) {
            if (temp(i) == max && !haveMax) {
              haveMax = true
            } else {
              temp(i) = temp(i) + 1
            }
            if (temp(i) != temp(0)) {
              sameNumber = false
            }
            result = sameNumber
          }
          temp.foreach(print)
          println
        }

        counter
      }

      def convertToTitle(n: Int): String = {

        var times: Int = n / 26
        var leftOver: Int = n % 26
        val result = new StringBuilder()

        while (times > 0) {
          if (leftOver != 0) {
            result.append((leftOver + 64).toChar)
          } else {
            times = times - 1
            result.append('Z')
          }
          leftOver = times % 26
          times = times / 26
        }

        if (leftOver != 0) {
          result.append((leftOver + 64).toChar)
        } else {
          times = times - 1
          result.append('Z')
        }

        if (times != 0) {
          result.append((times + 64).toChar)
        }

        result.reverse.toString()
      }

      def titleToNumber(s: String): Int = {

        var sum: Int = 0
        var counter = 0
        for (i <- s.length - 1 to 0 by -1) {
          val base = scala.math.pow(26, counter).asInstanceOf[Int]
          sum += base * (s(i).toInt - 64)
          counter += 1
        }
        sum
      }

      def createBTree(numbers: Array[Int]): TreeNode = {
        var base: TreeNode = null
        def insertBTree(number: Int): Unit = {
          var temp = base
          var done = false

          while (!done) {
            if (number > temp.value) {
              if (temp.right != null) {
                temp = temp.right
              } else {
                temp.right = new TreeNode(number)
                done = true
              }
            } else {
              if (temp.left != null) {
                temp = temp.left
              } else {
                temp.left = new TreeNode(number)
                done = true
              }
            }
          }
        }
        if (numbers.length > 0) {
          base = new TreeNode(numbers(0))
          for (i <- 1 to numbers.length - 1) {
            insertBTree(numbers(i))
          }
          base
        } else {
          null
        }
      }

      def getMinimumDifference(root: TreeNode): Int = {

        var smaller = None: Option[Int]
        var diff = Int.MaxValue
        loopTree(root)
        def loopTree(newRoot: TreeNode): Unit = {
          if (newRoot.left != null) {
            loopTree(newRoot.left)
          }
          if (!smaller.isEmpty) {
            val currentDiff = newRoot.value - smaller.get
            if (diff > currentDiff) {
              diff = currentDiff
            }
          }
          smaller = Some(newRoot.value)
          if (newRoot.right != null) {
            loopTree(newRoot.right)
          }
        }
        diff
      }

      def constructRectangle(area: Int): Array[Int] = {
        val squareRoot: Int = Math.sqrt(area.toDouble).toInt
        //println(squareRoot)
        var result = Array(area, 1)
        var found = false
        if (area == 0) {
          result = Array(0, 0)
        } else {
          for (i <- squareRoot to 2 by -1) {
            if (found == false && area % i == 0) {
              result = Array(area / i, i)
              found = true
            }
          }

        }
        result
      }

      def maxCount(m: Int, n: Int, ops: Array[Array[Int]]): Int = {

        var mMin = Int.MaxValue
        var nMin = Int.MaxValue

        if (ops.length != 0) {
          for (i <- ops) {
            if (i(0) < m && i(0) < mMin) {
              mMin = i(0)
            }
            if (i(1) < n && i(1) < nMin) {
              nMin = i(1)
            }
          }
          nMin * mMin
        } else {
          n * m
        }

      }

      def canConstruct(ransomNote: String, magazine: String): Boolean = {

        val table = new mutable.HashMap[Char, Int]()
        var result = true
        for (i <- magazine) {
          val value = table.getOrElse(i, 0) + 1
          table += (i -> value)
        }

        for (i <- ransomNote) {
          if (table.contains(i) && table.get(i).get > 0) {
            val newValue = table.get(i).get - 1
            table += (i -> newValue)
          } else {
            result = false
          }
        }
        result
      }

      def findContentChildren(g: Array[Int], s: Array[Int]): Int = {

        if (g.length != 0 || s.length != 0) {
          val baseNode = new Node(s(0), 1)
          def insert(valueToInsert: Int): Unit = {
            var done = false
            var currentBase = baseNode
            while (!done) {
              if (currentBase.value == valueToInsert) {
                currentBase.number += 1
                done = true
              } else if (currentBase.value < valueToInsert) {
                if (currentBase.right != null) {
                  currentBase = currentBase.right
                } else {
                  currentBase.right = new Node(valueToInsert, 1)
                  done = true
                }
              } else {
                if (currentBase.left != null) {
                  currentBase = currentBase.left
                } else {
                  currentBase.left = new Node(valueToInsert, 1)
                  done = true
                }
              }
            }
          }

          var result = 0
          def findCooke(greedyFactor: Int): Unit = {
            var done = false
            var currentBase = baseNode
            var biggestNode: Node = null
            while (!done) {
              if (greedyFactor == 119) {
                println(currentBase.value)
              }
              if (currentBase.value == greedyFactor) {
                if (currentBase.number != 0) {
                  biggestNode = currentBase
                  done = true
                } else if (currentBase.right != null) {
                  currentBase = currentBase.right
                } else {
                  done = true
                }
              } else if (currentBase.value > greedyFactor) {
                if (currentBase.number != 0) {
                  biggestNode = currentBase
                  if (currentBase.left != null) {
                    currentBase = currentBase.left
                  } else if (currentBase.number == 0 && currentBase.right != null) {
                    currentBase = currentBase.right
                  } else {
                    done = true
                  }
                } else if (currentBase.value < greedyFactor) {
                  if (currentBase.right != null) {
                    currentBase = currentBase.right
                  } else {
                    done = true //need to go back to where it path way 130 > 119
                  }
                }
                if (done == true && biggestNode != null && biggestNode.number != 0) {
                  biggestNode.number -= 1
                  result += 1
                  //println(s"this ${greedyFactor} get cookie ${biggestNode.value}")
                }
              }
            }
            for (i <- 1 to s.length - 1) {
              insert(s(i))
            }
            for (i <- 0 to g.length - 1) {
              findCooke(g(i))
            }
            result
          }
          result
        }else{
          0
        }
      }
    //val tree = new TreeMap(a2)
    //
    val kids = Array(262,437,433,102,438,346,131,160,281,34,219,373,466,275,51,118,209,32,108,57,385,514,439,73,271,442,366,515,284,425,491,466,322,34,484,231,450,355,106,279,496,312,96,461,446,422,143,98,444,461,142,234,416,45,271,344,446,364,216,16,431,370,120,463,377,106,113,406,406,481,304,41,2,174,81,220,158,104,119,95,479,323,145,205,218,482,345,324,253,368,214,379,343,375,134,145,268,56,206)
    val cookie = Array(149,79,388,251,417,82,233,377,95,309,418,400,501,349,348,400,461,495,104,330,155,483,334,436,512,232,511,40,343,334,307,56,164,276,399,337,59,440,3,458,417,291,354,419,516,4,370,106,469,254,274,163,345,513,130,292,330,198,142,95,18,295,126,131,339,171,347,199,244,428,383,43,315,353,91,289,466,178,425,112,420,85,159,360,241,300,295,285,310,76,69,297,155,416,333,416,226,262,63,445,77,151,368,406,171,13,198,30,446,142,329,245,505,238,352,113,485,296,337,507,91,437,366,511,414,46,78,399,283,106,202,494,380,479,522,479,438,21,130,293,422,440,71,321,446,358,39,447,427,6,33,429,324,76,396,444,519,159,45,403,243,251,373,251,23,140,7,356,194,499,276,251,311,10,147,30,276,430,151,519,36,354,162,451,524,312,447,77,170,428,23,283,249,466,39,58,424,68,481,2,173,179,382,334,430,84,151,293,95,522,358,505,63,524,143,119,325,401,6,361,284,418,169,256,221,330,23,72,185,376,515,84,319,27,66,497)
    println(findContentChildren(Array(262,437,433,102,438,346,131,160,281,34,219,373,466,275,51,118,209,32,108,57,385,514,439,73,271,442,366,515,284,425,491,466,322,34,484,231,450,355,106,279,496,312,96,461,446,422,143,98,444,461,142,234,416,45,271,344,446,364,216,16,431,370,120,463,377,106,113,406,406,481,304,41,2,174,81,220,158,104,119,95,479,323,145,205,218,482,345,324,253,368,214,379,343,375,134,145,268,56,206),Array(149,79,388,251,417,82,233,377,95,309,418,400,501,349,348,400,461,495,104,330,155,483,334,436,512,232,511,40,343,334,307,56,164,276,399,337,59,440,3,458,417,291,354,419,516,4,370,106,469,254,274,163,345,513,130,292,330,198,142,95,18,295,126,131,339,171,347,199,244,428,383,43,315,353,91,289,466,178,425,112,420,85,159,360,241,300,295,285,310,76,69,297,155,416,333,416,226,262,63,445,77,151,368,406,171,13,198,30,446,142,329,245,505,238,352,113,485,296,337,507,91,437,366,511,414,46,78,399,283,106,202,494,380,479,522,479,438,21,130,293,422,440,71,321,446,358,39,447,427,6,33,429,324,76,396,444,519,159,45,403,243,251,373,251,23,140,7,356,194,499,276,251,311,10,147,30,276,430,151,519,36,354,162,451,524,312,447,77,170,428,23,283,249,466,39,58,424,68,481,2,173,179,382,334,430,84,151,293,95,522,358,505,63,524,143,119,325,401,6,361,284,418,169,256,221,330,23,72,185,376,515,84,319,27,66,497)))
    }
  }
