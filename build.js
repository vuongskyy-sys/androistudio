// EduConnect - Production Build Script
// Packages, validates, and generates the production distribution for Netlify

const fs = require('fs');
const path = require('path');

console.log('\n======================================================');
console.log('   EDUCONNECT — PRODUCTION BUILD PROCESS');
console.log('======================================================');

const ROOT_DIR = __dirname;
const DIST_DIR = path.join(ROOT_DIR, 'dist');
const INDEX_HTML = path.join(ROOT_DIR, 'index.html');

// 1. Verify index.html exists
if (!fs.existsSync(INDEX_HTML)) {
  console.error('❌ Build Error: index.html not found in workspace root.');
  process.exit(1);
}

const htmlContent = fs.readFileSync(INDEX_HTML, 'utf8');

// 2. Health checks
console.log('🔍 Validating application integrity...');
const checks = [
  { name: 'Document Title', passed: htmlContent.includes('<title>') },
  { name: 'Viewport Meta Tag', passed: htmlContent.includes('name="viewport"') },
  { name: 'React Mount Root (#root)', passed: htmlContent.includes('id="root"') },
  { name: 'React & ReactDOM CDN', passed: htmlContent.includes('react@18') || htmlContent.includes('react.production') },
  { name: 'Tailwind CSS Engine', passed: htmlContent.includes('tailwindcss.com') },
  { name: 'Plus Jakarta Sans Font', passed: htmlContent.includes('Plus+Jakarta+Sans') },
  { name: 'JetBrains Mono Font', passed: htmlContent.includes('JetBrains+Mono') },
  { name: 'Vietnamese Charset UTF-8', passed: htmlContent.includes('charset="UTF-8"') }
];

let allPassed = true;
checks.forEach(c => {
  if (c.passed) {
    console.log(`  ✓ ${c.name}`);
  } else {
    console.warn(`  ⚠️ Warning: ${c.name} check missed`);
    allPassed = false;
  }
});

// 3. Prepare dist directory
if (fs.existsSync(DIST_DIR)) {
  fs.rmSync(DIST_DIR, { recursive: true, force: true });
}
fs.mkdirSync(DIST_DIR, { recursive: true });

// 4. Copy index.html
fs.writeFileSync(path.join(DIST_DIR, 'index.html'), htmlContent, 'utf8');
console.log(`📦 Generated: dist/index.html (${(fs.statSync(path.join(DIST_DIR, 'index.html')).size / 1024).toFixed(1)} KB)`);

// 5. Copy src directory if exists
const SRC_DIR = path.join(ROOT_DIR, 'src');
if (fs.existsSync(SRC_DIR)) {
  const DIST_SRC_DIR = path.join(DIST_DIR, 'src');
  fs.mkdirSync(DIST_SRC_DIR, { recursive: true });
  fs.cpSync(SRC_DIR, DIST_SRC_DIR, { recursive: true });
  console.log('📦 Copied src/ assets into dist/src/');
}

// 6. Generate Netlify _redirects for SPA routing
const redirectsContent = `/*    /index.html   200\n`;
fs.writeFileSync(path.join(DIST_DIR, '_redirects'), redirectsContent, 'utf8');
fs.writeFileSync(path.join(ROOT_DIR, '_redirects'), redirectsContent, 'utf8');
console.log('⚙️ Configured: Netlify _redirects (SPA fallback)');

// 7. Generate netlify.toml in dist and root
const netlifyTomlRoot = `[build]
  publish = "dist"
  command = "node build.js"

[[redirects]]
  from = "/*"
  to = "/index.html"
  status = 200
`;

const netlifyTomlDist = `[build]
  publish = "."

[[redirects]]
  from = "/*"
  to = "/index.html"
  status = 200
`;

fs.writeFileSync(path.join(ROOT_DIR, 'netlify.toml'), netlifyTomlRoot, 'utf8');
fs.writeFileSync(path.join(DIST_DIR, 'netlify.toml'), netlifyTomlDist, 'utf8');
console.log('⚙️ Configured: netlify.toml');

console.log('\n======================================================');
console.log('✅ PRODUCTION BUILD COMPLETED SUCCESSFULLY!');
console.log('======================================================');
console.log('Deploy Directory: ./dist');
console.log('Ready for Netlify Drag & Drop or Git automated deploy.');
console.log('======================================================\n');
