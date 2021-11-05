package com.example.myapplication

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*
import java.lang.reflect.Type
import java.net.HttpURLConnection
import java.net.URL

import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import java.net.InetAddress

private var filename: String = "database.json"
private val serverURL:String = "http://10.0.2.2:4567"

//TODO NOTES
//1. we're only going to have a single file with all the devices and categories
//2. upon startup, we just need to check whether database.json exists -> make if not
//3. need search helper functions for L1 and L2 and get for L3 (consider a pair<str,str> input)
//4. Taking care of time logging actions

class DatabaseModel(context: Context) {

    lateinit var database : HashMap<String, HashMap<String, MaintenanceRecord>>
    val context: Context = context
    var cm : ConnectivityManager
    var logs = arrayListOf<Pair<String, String>>()
    init {
        createFromFile(context)
        this.cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    /*
    Startup and shutdown file operations
     */

    private fun saveToLocalFile(jsonOutput: String) {
        println("DIR: " + context.filesDir)
        val file = File(context.filesDir, filename)
        val fileWriter = FileWriter(file)
        val bufferedWriter = BufferedWriter(fileWriter)
        bufferedWriter.write(jsonOutput)
        bufferedWriter.close()
    }

    private fun createFromFile(context: Context) {
        try {
            val file = File(context.filesDir, filename)
//            val file = File( "app/java/com/example/myapplication/exDB.json")
            val fileReader = FileReader(file)

            val bufferedReader = BufferedReader(fileReader)
            val stringBuilder = StringBuilder()
            val allLines: List<String> = bufferedReader.readLines()
            for (line in allLines) {
                stringBuilder.append(line).append("\n")
            }
            bufferedReader.close()

            val jsonInput = stringBuilder.toString()
            println("DB: $jsonInput")
            val hashMapType: Type = object : TypeToken<HashMap<String, HashMap<String, MaintenanceRecord>>?>() {}.type
            val readDB: HashMap<String, HashMap<String, MaintenanceRecord>> = Gson().fromJson(jsonInput, hashMapType)
            this.database = readDB
            println("DB2: ${this.database.keys}")
            println("DB3: ${this.database["Surgical ICU"]?.get("Surgical Masks")}")
            println("DB4: ${this.database["Surgical ICU"]?.get("Surgical Masks")?.inventoryNum}")

            //return readDB;
        } catch (e: FileNotFoundException) {
            saveToLocalFile("{\n" +
                    "  \"Surgical ICU\": {\n" +
                    "    \"Surgical Masks\": {\n" +
                    "      \"inventoryNum\": \"1234\",\n" +
                    "      \"workOrderNum\": \"64\",\n" +
                    "      \"serviceProvider\": \"MedTech\",\n" +
                    "      \"serviceEngineeringCode\": \"504\",\n" +
                    "      \"faultCode\": \"300\",\n" +
                    "      \"ipmProcedure\": \"Lorem Ipsum sit dollar...\",\n" +
                    "      \"status\": 0" +
                    "    },\n" +
                    "    \"Syringes\": {\n" +
                    "      \"inventoryNum\": \"1234\",\n" +
                    "      \"workOrderNum\": \"64\",\n" +
                    "      \"serviceProvider\": \"MedTech\",\n" +
                    "      \"serviceEngineeringCode\": \"504\",\n" +
                    "      \"faultCode\": \"300\",\n" +
                    "      \"ipmProcedure\": \"Lorem Ipsum sit dollar...\",\n" +
                    "      \"status\": 1" +
                    "    },\n" +
                    "    \"Blood Pressure Cuffs\": {\n" +
                    "      \"inventoryNum\": \"1234\",\n" +
                    "      \"workOrderNum\": \"64\",\n" +
                    "      \"serviceProvider\": \"MedTech\",\n" +
                    "      \"serviceEngineeringCode\": \"504\",\n" +
                    "      \"faultCode\": \"300\",\n" +
                    "      \"ipmProcedure\": \"Lorem Ipsum sit dollar...\",\n" +
                    "      \"status\": 1" +
                    "    }\n" +
                    "  },\n" +
                    "  \"Neonatal Ward\": {\n" +
                    "    \"Incubators\": {\n" +
                    "      \"inventoryNum\": \"1234\",\n" +
                    "      \"workOrderNum\": \"64\",\n" +
                    "      \"serviceProvider\": \"MedTech\",\n" +
                    "      \"serviceEngineeringCode\": \"504\",\n" +
                    "      \"faultCode\": \"300\",\n" +
                    "      \"ipmProcedure\": \"Lorem Ipsum sit dollar...\",\n" +
                    "      \"status\": 1" +
                    "    },\n" +
                    "    \"Pulse Oximeters\": {\n" +
                    "      \"inventoryNum\": \"1234\",\n" +
                    "      \"workOrderNum\": \"64\",\n" +
                    "      \"serviceProvider\": \"MedTech\",\n" +
                    "      \"serviceEngineeringCode\": \"504\",\n" +
                    "      \"faultCode\": \"300\",\n" +
                    "      \"ipmProcedure\": \"Lorem Ipsum sit dollar...\",\n" +
                    "      \"status\": 0" +
                    "    }\n" +
                    "  },\n" +
                    "  \"Cardiology Ward\": {\n" +
                    "    \"EKG Machines\": {\n" +
                    "      \"inventoryNum\": \"1234\",\n" +
                    "      \"workOrderNum\": \"64\",\n" +
                    "      \"serviceProvider\": \"MedTech\",\n" +
                    "      \"serviceEngineeringCode\": \"504\",\n" +
                    "      \"faultCode\": \"300\",\n" +
                    "      \"ipmProcedure\": \"Lorem Ipsum sit dollar...\",\n" +
                    "      \"status\": 2" +
                    "    },\n" +
                    "    \"Defibrillators\": {\n" +
                    "      \"inventoryNum\": \"1234\",\n" +
                    "      \"workOrderNum\": \"64\",\n" +
                    "      \"serviceProvider\": \"MedTech\",\n" +
                    "      \"serviceEngineeringCode\": \"504\",\n" +
                    "      \"faultCode\": \"300\",\n" +
                    "      \"ipmProcedure\": \"Lorem Ipsum sit dollar...\",\n" +
                    "      \"status\": 2" +
                    "    }\n" +
                    "  }\n" +
                    "}")
            //return database
            return createFromFile(context)
        }
    }

    /*
    Getters and Setters for our DatabaseModel class
     */

    fun fragment_get(category: String = "", device: String = ""): Any? {
        if (category != "") {
            if (device != "") {
                val catMap = database.get(category)
                if (catMap != null) {
                    return catMap.get(device)
                } else {
                    error("devices L ")
                }
            } else {
                val catMap = database.get(category)
                if (catMap != null) {
                    // TODO: Modify to return a pairing of Device Name and Status
                    val deviceKeys = catMap.keys
                    val statusAndDevices = ArrayList<Pair<String, String>>()
                    for (key in deviceKeys) {
                        val currentDevice = catMap.get(key)
                        if (currentDevice != null) {
                            statusAndDevices.add(Pair(key, currentDevice.status))
                        }
                    }
                    println("STATUS: $statusAndDevices")
                    return deviceKeys
                }
                else {
                    error("categories L")
                }
            }
        } else if (category == "" && device == "") {
            return database.keys
        } else {
            error("device info L")
        }
    }

    fun fragment_set(category: String = "", device: String = "", MR: MaintenanceRecord) {
        val json = Gson().toJson(MR)
        val cat = database.get(category)

        MR.timestamp = (System.currentTimeMillis() / 1000).toInt()

        if (cat != null) {
            cat.put(device, MR)
        }

        val fullDBJson = Gson().toJson(database)
        println("SAVING FILE")
        saveToLocalFile(fullDBJson)

        val url = serverURL + "/DB/" + category + "/" + device +"/"
//        post_server(url, json)
        logging(url, json)
    }


//==============================BACKEND Functions ===============================================
    fun logging(url: String, json: String) {
        logs.add(Pair(url, json))
        sync()
    }

    fun sync() {
        Thread {
            while (isOnline() && logs.size > 0) {
                val log = logs.removeAt(0)
                val response = post_server(log.first, log.second)
                if (response != 200) {
                    logs.add(0, log)
                    break
                }
            }
        }.start()
    }


    fun post_server(url:String, json:String) : Int {
        //val url2 = url.replace(" ", "_")
        Log.i("postServer0:",url)
        val mURL = URL(url)
        Log.i("postServer0.5:",mURL.toString())
        //val reqParam = URLEncoder.encode(json, "ascii")
        val reqParam = json
        var code:Int
        Log.i("postServer1:",json)

        var urlc:HttpURLConnection = mURL.openConnection() as HttpURLConnection
        try {
            urlc.connectTimeout = (10*1000)
            urlc.requestMethod = "POST"
            val wr = OutputStreamWriter(urlc.getOutputStream())
            wr.write(reqParam)
            wr.flush()
            urlc.connect()
            Log.i("postServer1.5:",urlc.responseCode.toString())

        } catch (e: IOException) {
            Log.i("postServer1.5:","404")
            return 404
        }
        with(urlc) {

            println("URL : $url")
            println("Response Code : $responseCode")
            code = responseCode

            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()

                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                println("Response : $response")
            }
        }
        return code
    }


    fun get2_server(url:String) : String {
        //val url2 = url.replace(" ", "_")
        Log.i("postServer0:",url)
        val mURL = URL(url)
        Log.i("postServer0.5:",mURL.toString())
        //val reqParam = URLEncoder.encode(json, "ascii")
        val out = StringBuffer()
        var code:Int

        var urlc:HttpURLConnection = mURL.openConnection() as HttpURLConnection
        try {
            urlc.connectTimeout = (10*1000)
            urlc.requestMethod = "GET"
            urlc.connect()
            Log.i("postServer1.5:",urlc.responseCode.toString())

        } catch (e: IOException) {
            Log.i("postServer1.5:","404")
            return ""
        }
        with(urlc) {

            println("URL : $url")
            println("Response Code : $responseCode")
            code = responseCode

            BufferedReader(InputStreamReader(inputStream)).use {
                var inputLine = it.readLine()
                while (inputLine != null) {
                    out.append(inputLine)
                    inputLine = it.readLine()
                }
                println("Response : $out")
            }
        }
        return out.toString();
    }

    fun get_server(url:String): String {
        return get2_server(url);
        Log.i("url", url)
        val URLm = URL(url)

        Log.i("URLm", URLm.toString())
        val text1 = URLm.readText()
        Log.i("text1", text1)
        //var text = StringBuffer()
        //with(URLm.openConnection() as HttpURLConnection) {
        //    requestMethod = "GET"  // optional default is GET
//
        //    Log.i("test1","\nSent 'GET' request to URL : $url; Response Code : $responseCode")
//
        //    inputStream.bufferedReader().use {
        //        val response = StringBuffer()
//
        //        var inputLine = it.readLine()
        //        while (inputLine != null) {
        //            println(inputLine)
        //            response.append(inputLine)
        //            inputLine = it.readLine()
        //        }
        //        text = response
        //    }
        //}
        Log.i("response:", text1)
        return text1
    }

    fun isOnline(): Boolean {
        val net = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.cm.getActiveNetwork()
        } else {
            this.cm.getActiveNetworkInfo()
        }
        return net != null
    }

    /**
     * Update DB with backendDB
     */
    fun updateDB() {
        if (isOnline()) {
            Thread {
                val newDB = get_server("$serverURL/DB/")
                val hashMapType: Type =
                    object :
                        TypeToken<HashMap<String, HashMap<String, MaintenanceRecord>>?>() {}.type
                val readDB: HashMap<String, HashMap<String, MaintenanceRecord>> =
                    Gson().fromJson(newDB, hashMapType)
                this.database = readDB

                val fullDBJson = Gson().toJson(database)
                println("SAVING Web to FILE")
                saveToLocalFile(fullDBJson)
            }.start()
        }
    }
}

