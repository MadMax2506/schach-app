package janorschke.meyer.service.model

abstract class BaseXml {
    protected fun <T> listToArrayList(list: MutableList<T>): ArrayList<T> {
        val res = ArrayList<T>()

        for (elem in list) {
            res.add(elem)
        }

        return res
    }
}