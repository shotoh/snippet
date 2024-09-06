import React from 'react';

export const SimpleBody = () => {
    return (
        <div className="p-4 space-y-4">
            <section id="home">
                <h1 className="text-4xl font-bold">Welcome</h1>
                <p>Macrosoft is a Pomona-based tech startup company, specializing in social media platforms</p>
            </section>
            <section id="products">
                
                <h1 className="text-4xl font-bold">Products</h1>
                <div className="m-2" id="products">
                <h2 className="text-2xl text-blue-800 font-bold"><a href="/socialmedia">Social Media Platform</a></h2>
                </div>
                
            </section>
            <section id="about">
            <h1 className="text-4xl font-bold">Who We Are</h1>
                <p>We are a team of passionate developers who are developing the next large-scale social media platform</p>
            </section>
            <section id="contact">
            <h1 className="text-4xl font-bold">Contact Methods</h1>
                <p>Do not contact us.</p>
            </section>
        </div>
    );
};

export default SimpleBody;
