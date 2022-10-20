package com.example.comp5216_petloversapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.comp5216_petloversapp.databinding.ItemNewsBinding;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.VH> {
    private List<News> data;

    public NewsAdapter() {
        this.data = new ArrayList<>();
        data.add(new News("The Petspiration Foundation partners with Assistance Dogs Australia", "by Thomas Oakley-Newell \n\n\nThe Petspiration Foundation has announced a three-year partnership with Assistance Dogs Australia (ADA) that will see $195,000 donated over that period.\n" +
                "\n" +
                "The donation includes $30,000 towards the development of ADA’s upcoming Sensory Garden and $105,000 towards funding all placement packs throughout the partnership.\n" +
                "\n" +
                "Tara Cheesman, National Major Relationships Manager at ADA, said The Foundation has been a respected partner for many years and they are excited to continue into a longer-term agreement.\n" +
                "\n" +
                "“We’ll be able to provide many more Australians with the essential support and day-to-day guidance they need to be part of their respective communities thanks to the long-term support of The Foundation.\n" +
                "\n" +
                "“The new Sensory Garden will help improve the training of our dogs, and the placement of newly graduated dogs with clients will not only provide us with financial aid, but also help us open countless more doors for them as individuals.”\n" +
                "\n" +
                "Also throughout October, The Foundation will support ADA’s Dogtober campaign which encourages the community to organise fundraising events to help ADA continue to train highly intelligent, specially bred dogs to help people living with disabilities across Australia.\n" +
                "\n" +
                "Sherralea Cassidy, Charity and Events Lead at The Petspiration Foundation, said the partnership will go a long way in helping ADA raise and place trained dogs with clients.\n" +
                "\n" +
                "“We are proud to be able to support like-minded organisations like ADA who are committed to empowering a better future for both pets and people.\n" +
                "\n" +
                "“Since first partnering with ADA in 2017, we’ve witnessed first-hand the positive impact that their work has on the lives of pets and people across the country.\n" +
                "\n" +
                "“Providing Australians with better access to emotional and physical support through pets is extremely powerful, so we’re so excited to join ADA in October – and over the next three years – to help provide even more Australians with the support that they need.”\n" +
                "\n", "October 17, 2022", R.drawable.news_1));
        data.add(new News("Laila and Me launch crowdfunding campaign due to growing demand for healthy pet treats", "by Rachel White\n\n\nPremium Australian dog treat makers Laila and Me launch a crowdfunding campaign, offering shares from $250 to prospective investors to match growing demand for healthy pet treats.\n" +
                "\n" +
                "Founded in 2015, Laila and Me make freeze-dried pet treats that are single-ingredient, human grade and locally sourced. All products are made in Australia and sold exclusively online.\n" +
                "\n" +
                "Appealing a growing market of dog owners looking to avoid treats full of preservatives, artificial colours, and flavours, the team at Laila and Me pride themselves on providing healthy alternatives that help pets live happier and healthier lives.\n" +
                "\n" +
                "With two warehouses in Melbourne and Bendigo operating at capacity, the company is now taking the next step towards international expansion, with current demand exceeding their existing capacity.\n" +
                "\n" +
                "According to data provided by the company, last financial year saw revenue for their gourmet pet treats increase by 48 per cent, on top of a massive 228 per cent increase the year before.\n" +
                "\n" +
                "Pet food production is a three-billion-dollar industry according to the Pet Food Production in Australia market research report from IBISworld released in August this year.\n" +
                "\n" +
                "The report highlights the growing demand for premium pet products due to owners generally being time-poor and opting to buy high-quality manufactured pet foods rather than making health alternatives at home.\n" +
                "\n" +
                "Laila and Me report a 0.03 per cent market share in the 2021-22 financial year, giving them a significant opportunity to grow in the coming years to match consumer demand.\n" +
                "\n" +
                "The company says they’ve reinvested every available cent back into the company over the past few years but need more funding to take their operations to the next level.\n" +
                "\n" +
                "The crowdfunding campaign is now open and will continue for two weeks.\n" +
                "\n", "October 17, 2022", R.drawable.news_2));
        data.add(new News("A new home for thousands of cats and dogs used in animal research", "by Rachel White\n\n\nAmendments to the Animal Research Act (1985) passed last week makes provision for the rehoming of cats and dogs used in animal research.\n" +
                "\n" +
                "The Animal Justice Party’s Right to Release Bill passed on October 13, has been applauded by Stephen Coleman, CEO of the Royal Society for the Prevention of Cruelty to Animals NSW (RSPCA NSW). \n" +
                "\n" +
                "“We would like to congratulate the Honorable Emma Hurst MLC and her team for drafting this bill, and Alex Greenwich MP who took carriage in the Legislative Assembly, helping to give all creatures great and small a voice in the NSW Parliament,” he said.\n" +
                "\n" +
                "The new Bill will allow thousands of animals the chance to live safe and normal lives instead of being arbitrarily put down when their time as testing subjects ends, as is routinely the case.\n" +
                "\n" +
                "The Bill is affectionally known as “Buddy’s Law” in honour of Buddy, a Beagle who spent the first eight years of his life as a research subject. Patrice Pandelos, Buddy’s owner, fought hard for the bill to be passed to allow other cats and dogs to have a life after research, just like Buddy.\n" +
                "\n" +
                "“I’d also like to personally thank Patrice Pandelos, who is a loyal member of RSPCA NSW, and of course, the owner of Buddy, a Beagle who was subjected to scientific experiments for the first eight years of his life,” said Coleman.\n" +
                "\n" +
                "“Patrice’s drive and determination to improve the lives of animals like Buddy is a testament to her passion for animal welfare, and we are incredibly proud to have her as a member of our community.”\n" +
                "\n" +
                "In 2020 almost 1000 dogs and over 500 cats were used in animal experimentation in NSW, with none of those dogs and only 75 of the cats being rehomed post-research.\n" +
                "\n" +
                "Coleman says RSPCA NSW is looking forward to collaborating with the State Government, the Department of Primary Industries, research organisations and other animal rehoming groups over the coming months as the new bill is implemented. ", "October 17, 2022", R.drawable.news_3));
        data.add(new News("Cyber pets: dog ownership in the digital age", "by Rachel White\n\n\nA national survey has found almost 70 per cent of dog owners have uploaded images of their canine companions to social media and almost 30 per cent have set up accounts exclusively for their pets.\n" +
                "\n" +
                "The research carried out by YouGov on behalf of Orivet, a Melbourne-based pet health biotechnology company, was conducted via an online survey of dog owners of various ages and situations across the country.\n" +
                "\n" +
                "Unsurprisingly, younger urban-dwelling dog owners are far more likely to feature their furry friends online, with Millennials most likely to upload pictures (82 per cent), followed by Gen X (73 per cent) and Gen Z (72 per cent).\n" +
                "\n" +
                "Dog owners of all ages with designer ‘oodle’ varieties are the most likely group to feature pictures of their dogs online (82 per cent) and set up accounts for their pets (53 per cent).\n" +
                "\n" +
                "When it came to health advice, online portals like Google and WebMD for Pets were the second most commonly accessed, with the local veterinarian still being the most relied-upon source of information for all age groups, whether living in an urban or rural setting.\n" +
                "\n" +
                "George Sofronidis, CEO of Orivet, said he was most surprised by the unconditional love lavished on our dogs.\n" +
                "\n" +
                "“What struck me most was how forgiving we are of our canine companions. Almost half of the owners surveyed admit their dogs have caused some havoc around the house,” he said.\n" +
                "\n" +
                "“Yet still we love them, as despite these transgressions, many dogs are treated like treasured family members and more than four in ten pet owners said they allowed their dog to sleep under the covers in their bed.”\n" +
                "\n" +
                "Common complaints by participants included dogs coming inside wet or muddy and shaking themselves dry, eating socks, underwear or other clothing, destroying furniture and hiding bones that slowly rot over time.\n" +
                "\n" +
                "Some things, though, never change, with a ball being by far a dog’s most favoured toy, closely followed by plush toys, according to the surveyed participants.", "October 17, 2022", R.drawable.news_4));
        data.add(new News("Podcast Episode 8: Shiva Greenhalgh, Pet Nutritionist", "by Thomas Oakley-Newell\n\n\nWelcome to the Pet Industry News Podcast, where we chat with industry leaders, and experts in their field, about all things affecting the pet industry.\n" +
                "\n" +
                "This episode we speak to Shiva Greenhalgh, pet nutritionist, about the differences between wet and dry cat food, what cats need in their diet at various stages in their life, and the regulation of pet food.\n" +
                "\n", "September 5, 2022", R.drawable.news_5));
        data.add(new News("Podcast Episode 9: Jenny Richards, The Paw Grocer",  "by Thomas Oakley-Newell\n\n\nWelcome to the Pet Industry News Podcast, where we chat with industry leaders, and experts in their field, about all things affecting the pet industry.\n" +
                "\n" +
                "In this episode we speak to Jenny Richards, Founder of The Paw Grocer, about how she set up her business, the challenges she faced, and the benefits of freeze-dried treats. \n" +
                "\n", "September 19, 2022", R.drawable.news_6));
        data.add(new News("Australia’s Top 10 most popular pets revealed", "by Intermedia\n\n\nAustralia has one of the highest rates of pet ownership in the world, with more than 25 million pets large and small being housed across the nation. A recent study by the Pet Industry Association Australia confirmed that more Aussies live with an animal than a child, and has put together a definitive list of Australia’s top ten pets.\n" +
                "\n" +
                "Editor-in-Chief for Love Your Pets magazine, Dr. Shibly Mustapha, said that the list was extremely varied, showing the determination of Australians to let animals into their home.\n" +
                "\n" +
                "“The top ten list includes animals as small as goldfish to ones as expensive and high maintenance as horses. It’s a really insightful snapshot into just how important the presence of animals is within the typical Australian home,” Dr. Mustapha said.\n" +
                "\n" +
                "“There are many benefits to having a pet: they encourage exercise, social interaction, companionship, promote mental health, and teach responsibility and love. Pets outnumber people in Australia – even if it’s a little fish in a tank, Aussies will do what they can to include animals in their home,” he said.\n" +
                "\n" +
                "Australia’s Top Ten Favourite Pets list is as follows:\nWho let the dogs out? Approximately 36 million Aussies! Man’s best friend is Australia’s #1 favourite pet, with that’s two in every five households owning at least one dog.\n" +
                "Paws up! Cats come in a close second, with approximately three in every ten homes making space for the furry feline companions. That’s almost 16 cats for every 100 people.\n" +
                "Surf’s up: around 8.7 million fish are live as pampered pets in tanks across the nation. The easy upkeep and low maintenance make them an ideal pet for small living spaces.\n" +
                "Birds of all shapes, sizes and squawks are the fourth favourite Aussie pet, with 4.2 million being invited to nest indoors. The varied list includes cockatiels, budgies and finches which are easy to take care of and make for easy company.\n" +
                "Cute and cuddly bunny rabbits take fifth position on the list. Known for their individual personalities, some bunnies are naturally social while others keep to themselves. The overall winning characteristic: they are unbelievably cute.\n" +
                "Slow and steady wins the race: Turtles are cute, compact and relatively low maintenance. They’re a little more complex to take care of than your average furry friend but are a definite talking point that visitors and family members will adore.\n" +
                "Horses for courses: most people would love to have a pony to trot around on, and some are lucky enough to. The downside of this fabulous pet is that they are extremely expensive, high maintenance and require a lot of space.\n" +
                "Slinky and scaly, snakes sneak onto the list at #8. Surprisingly low maintenance, all snakes need to be happy is a warm place to curl up, something to coil around and to be offered their favourite snacks from time to time.\n" +
                "Ferrets flock in at #9. The cute little creatures have a lot more attitude than other furry pets, but are smaller and require a little less attention. They can be a little bitey though, so watch your fingers!\n" +
                "Bearded dragons are native to Australia and roll in at #10 on the favourite pets list. While they have complex nutritional and environmental conditions, they’re much lower maintenance than a dog that needs to be walked every day.\n" +
                "For more information on which pet suits you best and how to best look after them, pick up a copy of Love Your Pets magazine which is available from media@readpublishing.group", "September 30, 2019", R.drawable.news_7));
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(ItemNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        News news = this.data.get(position);
        holder.binding.tvTitle.setText(news.title);
        holder.binding.tvContent.setText(news.description);
        Glide.with(holder.itemView).load(news.image).into(holder.binding.face);
        holder.binding.time.setText(news.time);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                context.startActivity(new Intent(context, DetailNewsActivity.class).putExtra("news", news));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        ItemNewsBinding binding;

        public VH(ItemNewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
