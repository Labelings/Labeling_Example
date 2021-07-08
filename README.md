# Labeling_Example
Example (tutorial) project. New to Labelings? Going through this might be the easiest way to start... (covers the Python and Java side of things).


## 1. Quick content overview
This project is meant to be run in succession.
1. Run the [Jupyter Notebook](https://jupyter.org/) found in 01_Python.
    The [tutorial.ipynb](/01_Python/tutorial.ipynb) is the notebook you need to start. No other steps needed.
    The notebook contains **one** of the methods to use it, as well as adding user-defined metadata

2. Run the [JavaExampleRunner](/02_Java/src/main/java/JavaExampleRunner.java).
    The main-method has 3 lines, one for each method showing one of the possible usages of the library in Java.
    * The first one is the most basic and common usage. It requires the Python example to be run once.
    * The second one is similar to the first, but contains added metadata. It shows how to create a class and a codec to (de-)serialize the class.
    * The last method uses a class as a label instead of an integer as the examples before. This class must be created by the user, same as with meta data.
    This feature is currently not available on the python part, but will be added in the next release.
      
## 2. Detailed information

### 2.3 In-Detail explanation of usage
In Python:
First, you have to create a new Labeling object with a fitting numpy-dtype and size (as tuple) to accommodate your data.
The Python library allows three ways to add data to a Labeling object.
1. A list of images as a convenience method
2. A single images
3. Patches of an image, containing a fully enclosed segment. If the segment is split, each part will count as its own segment

The new data is parsed pixel-wise and added to the existing Labeling. Except the list of images, each method returns a view on the changes that have been added to the Labeling.
Now, add any relevant metadata you want to add to this Labeling with add_metadata().
After adding all the data you want to add, you can either:
* get the bson and resulting image with get_result() or
* save it to a specified path with save_result(Path)

There are two constructor methods to load:
* from_values(), which is used to create an empty Labeling
* from_file(), to load already created data from either Python or java in python

In Java:
In Java, the usage is different, as this library is intended to be a link to [ImgLib2](https://imagej.net/libs/imglib2/) and [Fiji](https://imagej.net/software/fiji/index).
First you will need to either create or get a scijava context.
Then, get the LabelingIOService through the context.
The service exposes several methods to load and save ImgLabeling objects with or without metadata and with different kinds of Label information, just like in the python implementation. 
If you decide to I/O a file with metadata, the methods need a Container POJO as argument or return one.
If you use metadata or have non-primitive labels, you need to provide the class and a codec to de- and encode that class. Examples for such codecs can be found in this Example as well as in the actual implementation.

### 2.2 BSON-File data structure (as json)
```json
{
  "version": 1, // the version of the datastructure
  "numSets": 5, // the number of label sets in the file
  "numSources": 1, // the number of segmentations that have been merged into one
  "indexImg": "example1_1.tif", // the image containing the info described here
  "labelMapping": {}, // a mapping from integer to a class if applicable
  "labelSets": { // the label sets, mapping pixel value to label, the label can be any primitive type, otherwise the mapping is used
    "0": [], 
    "1": [1, 2], 
    "2": [2, 3], 
    "3": [1, 4], 
    "4": [3, 4]
  }, 
  "metadata": { // the free metadata field
    "author": "Tom Burke"
  }
}
```

## 3. How to use the libraries in your project

The python part can easily be installed like this:
```shell
pip install labeling
```

The java part can easily be installed like this as a maven dependency:
```xml
<dependencies>
    <dependency>
        <groupId>net.imglib2</groupId>
        <artifactId>labeling</artifactId>
        <version>0.2.2</version>
    </dependency>
</dependencies>

<repositories>
    <!-- NB: for SciJava dependencies -->
    <repository>
        <id>scijava.public</id>
        <url>https://maven.scijava.org/content/groups/public</url>
    </repository>
</repositories>
```