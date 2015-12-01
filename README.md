# NaNoGenMo-2015-entry

This maven / java project will take two project gutenberg books and transplant the most common words of a particular part-of-speech from a donor book to another.  *e.g.*: Transplanting from *Jewels of Gwahlur* by Robert E. Howard to *The Adventures of Tom Sawyer* by Mark Twain, transplanting *proper nouns* will change instances of "Tom" into "Conan".

## Usage

1. Download 2+ project gutenberg plain-text books to the `src\main\resources` folder. 
2. Build the project with `mvn clean package` and then 
3. run with `java -jar target\NaNoGenMo2015-0.1.0-SNAPSHOT-jar-with-dependencies.jar <donor-book-filename> <patient-book-filename>`
 
## Sample

> ... 
>
> "All right, **Zargheba**, it's a whiz! Come along, old chap, and I'll ask the widow to let up on you a little, **Zargheba**."
> 
>  "Will you, **Conan**-- now will you? That's good. If she'll let up on some of the roughest things, I'll smoke private and cuss private, and crowd through or bust. When you going to start the gang and turn robbers?"
> 
>  "Oh, right off. We'll get the boys together and have the initiation tonight, maybe."
> 
>  "Have the which?"
> 
>  "Have the initiation."
> 
>  "What's that?"
> 
>  "It's to swear to stand by one another, and never tell the gang's secrets, even if you're chopped all to flinders, and kill anybody and all his family that hurts one of the gang."
> 
>  "That's gay-- that's mighty gay, **Conan**, I tell you."
> 
>  "Well, I bet it is. And all that swearing's got to be done at midnight, in the lonesomest, awfulest place you can find-- a ha 'nted house is the best, but they're all ripped up now."
> 
>  "Well, midnight's good, anyway, **Conan**."
> 
>  "Yes, so it is. And you've got to swear on a coffin, and sign it with blood."
> 
>  "Now, that's something _ like _! Why, it's a million times bullier than pirating. I'll stick to the widder till I rot, **Conan**; and if I git to be a reg 'lar ripper of a robber, and everybody talking 'bout it, I reckon she'll be proud she snaked me in out of the wet."
> 
>  CONCLUSION SO endeth this chronicle. It being strictly a history of a _ boy _, it must stop here; the story could not go much further without becoming the history of a _ man _. When one writes a novel about grown people, he knows exactly where to stop-- that is, with a marriage; but when he writes of juveniles, he must stop where he best can.
> 
>  Most of the characters that perform in this book still live, and are prosperous and happy. Some day it may seem worth while to take up the story of the younger ones again and see what sort of men and women they turned out to be; therefore it will be wisest not to reveal any of that part of their lives at present.
