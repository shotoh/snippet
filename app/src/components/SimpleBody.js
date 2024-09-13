import React from 'react';
import { Link } from 'react-router-dom';

export const SimpleBody = () => {
    return (
        <div className="p-4 space-y-4">
            <section id="home">
                <h1 className="text-4xl font-bold">Welcome</h1>
                <p>Macrosoft is a Pomona-based tech startup company, specializing in social media platforms</p>
                <p>You might heard of our revolutionary platform: <Link to="/home/products" className="font-bold">Snip/it</Link></p>
            </section>
            </div>
            
    );
};

export default SimpleBody;
