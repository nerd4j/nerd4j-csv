# nerd4j-csv
Framework for read, write CSV files and perform mapping between custom Java data models and CSV records.
So far this is the quickest, easiest and most configurable open source Java framework for CSV file manipulation.

## Quick Start

This framework is also available on Maven Central [here] (http://search.maven.org/#artifactdetails|org.nerd4j|nerd4j-csv|1.0.0|jar "Maven Central: nerd4j-csv") and can be used with the following dependecy declaration:
```xml
<dependency>
 <groupId>org.nerd4j</groupId>
 <artifactId>nerd4j-csv</artifactId>
 <version>1.0.0</version>
</dependency>
```

If you need to read data from a CSV file into your own data model, work with it, maybe change the data and write it back into a CSV file you just need to follow few easy steps.

First write you model bean and annotate it with our annotations:
```java
@CSVReader
@CSVWriter
public class Model
{

  private String name;
  private Integer age;
  
  public Model()
  {
    super();
    
    this.name = null;
    this.age  = null;
  }
  
  @CSVColumn
  public String getName()
  {
    return this.name;
  }
  
  public void setName( String name )
  {
    this.name = name;
  }
  
  @CSVColumn
  public Integer getAge()
  {
    return this.age;
  }
  
  public void setAge( Integer age )
  {
    this.age = age;
  }
}
```

That's it!! Mapping is done!  
__@CSVReader__ and __@CSVWriter__ tell the framework that the configuration for a __CSVReader__ and a __CSVWriter__ should be prepared inferring the mapping information from the fields annotated with __@Column__.

Now you can produce _CsvReader_ and _CSVWriter_ factories using this lines of code:

```java
  // This factory is able to pase annotations and XML files and produce a CSVConfiguration.
  final CSVConfigurationFactory configurationFactory = new CSVConfigurationFactory();

  // The factory reads the annotations in the given class and creates the related CSVConfiguration.
  final CSVConfiguration configuration = configurationFactory.getCSVConfiguration( Model.class );
	
  // This factory reads the given CSVConfiguration and produces all the objects we need.
	final CSVFactory factory = new CSVFactory( configuration );

  // This factory produces CSVReaders properly configured to populate our model.
  final CSVReaderFactory readerFactory = factory.getCSVReaderFactory( Model.class.getCanonicalName() );

  // This factory produces CSVWriters properly configured to write our model into a CSV record.
  final CSVWriterFactory writerFactory = factory.getCSVWriterFactory( Model.class.getCanonicalName() );
```

You are almost done, all you have to do now is read and write your CSV files.  
The _CSVReaderFactory_ produces a _CSVReader_ for a given file, working with factories is not much work for a single file and becomes very convenient when you need to read a lot of files with the same model.

Now let's do the job:
```java
  try( 
    final CSVReader<Model> reader = readerFactory.getCSVReader( new File("source.csv") );
    final CSVWriter<Model> writer = writerFactory.getCSVWriter( new File("target.csv") );
  )
  {
    Model model = null;
    while( ! reader.isEndOfData() )
    {
      model = reader.readModel();
      // Do something....
      writer.writeModel( model );
    }
  }catch( IOException ex )
  {
    // Handle IO Exception
  }catch(CSVToModelBindingException | ModelToCSVBindingException ex )
  {
    // Handle Model Binding Exception
  }
```

That's it!!  
With a few lines of code you are able to read and write CSV files using your own data model.
But the framework is highly configurable and can cover almost all the needs that a developer can face working on CSV files.

For more details about the features of the nerd4j-csv framework see our [wiki](https://github.com/nerd4j/nerd4j-csv/wiki).

